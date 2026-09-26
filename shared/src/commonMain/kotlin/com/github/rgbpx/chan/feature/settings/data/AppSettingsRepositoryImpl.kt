package com.github.rgbpx.chan.feature.settings.data

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.settings.domain.AppSettings
import com.github.rgbpx.chan.feature.settings.domain.AppSettingsRepository
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import okio.FileSystem
import okio.Path.Companion.toPath

@SingleIn(AppScope::class)
@Inject
class AppSettingsRepositoryImpl(
    private val dataStore: DataStore<AppSettings>,
) : AppSettingsRepository {
    override val settings = dataStore.data

    override suspend fun setFirstLaunchCompleted() {
        dataStore.updateData { it.copy(firstLaunch = false) }
    }

    override suspend fun readCorruptedFileContent(corruptedFileName: String): String {
        val corruptedFilePath = FileKit
            .filesDir
            .resolve(corruptedFileName)
            .path
            .toPath()

        return FileSystem.SYSTEM.read(corruptedFilePath) {
            readUtf8()
        }
    }

    override suspend fun resetCorruptedFile() {
        dataStore.updateData {
            it.copy(corruptedBackupFileName = null)
        }
    }
}
