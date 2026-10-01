package com.github.rgbpx.chan.feature.settings.data.repository

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.settings.domain.model.AppSettings
import com.github.rgbpx.chan.feature.settings.domain.repository.AppSettingsRepository
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.FileSystem
import okio.Path.Companion.toPath

@SingleIn(AppScope::class)
@Inject
class AppSettingsRepositoryImpl(
    private val dataStore: DataStore<AppSettings>,
) : AppSettingsRepository {
    override val settings: Flow<AppSettings> = dataStore.data

    override suspend fun setFirstLaunchCompleted() {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(firstLaunch = false)
        }
    }

    override fun readCorruptedFileContent(
        corruptedFileName: String,
    ): Flow<String> = flow {
        val corruptedFilePath = FileKit
            .filesDir
            .resolve(corruptedFileName)
            .path
            .toPath()

        emit(
            FileSystem.SYSTEM.read(corruptedFilePath) {
                readUtf8()
            }
        )
    }

    override suspend fun resetCorruptedFile() {
        dataStore.updateData { currentSettings ->
            currentSettings.copy(backupFilename = null)
        }
    }
}
