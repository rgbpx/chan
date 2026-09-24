package com.github.rgbpx.chan.settings

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.di.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.time.Clock

@SingleIn(AppScope::class)
@Inject
class AppSettingsRepositoryImpl(
    private val dataStore: DataStore<AppSettings>,
) : AppSettingsRepository {
    override val settings = dataStore.data

    override suspend fun setFirstLaunchCompleted() {
        dataStore.updateData { it.copy(firstLaunch = false) }
    }

    override suspend fun readCorruptedFileContent(): String {
        val corruptedFilePath = FileKit
            .filesDir
            .resolve(appMetadata.settingsFileName)
            .path
            .toPath()

        return FileSystem.SYSTEM.read(corruptedFilePath) { readUtf8() }
    }

    override suspend fun backupCorruptedFile() {
        val corruptedFilename = appMetadata.settingsFileName
        val corruptedFilePath = FileKit
            .filesDir
            .resolve(corruptedFilename)
            .path
            .toPath()

        val unixTimeMs = Clock.System.now().toEpochMilliseconds()
        val backupFilename =
            "${corruptedFilename}.corrupted-${unixTimeMs}"
        val backupPath = FileKit
            .filesDir
            .resolve(backupFilename)
            .path
            .toPath()

        FileSystem.SYSTEM.atomicMove(corruptedFilePath, backupPath)
    }

    override suspend fun resetCorruptedFile() {
        dataStore.updateData { AppSettings() }
    }
}
