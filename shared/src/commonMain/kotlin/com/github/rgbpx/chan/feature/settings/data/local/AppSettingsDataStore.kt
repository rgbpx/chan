package com.github.rgbpx.chan.feature.settings.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.core.okio.OkioStorage
import com.github.rgbpx.chan.app.data.appMetadata
import com.github.rgbpx.chan.feature.settings.domain.model.AppSettings
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.time.Clock

internal fun createDataStore(
    fileName: String = appMetadata.settingsFileName
): DataStore<AppSettings> {
    fun pathOf(name: String) = FileKit
        .filesDir
        .resolve(name)
        .path
        .toPath()

    return DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = AppSettingsSerializer,
            producePath = { pathOf(fileName) },
        ),
        corruptionHandler = ReplaceFileCorruptionHandler {
            val unixTimeMs = Clock.System.now().toEpochMilliseconds()
            val backupFilename = "$fileName.backup-$unixTimeMs"
            val corruptedFilePath = pathOf(fileName)
            val backupPath = pathOf(backupFilename)

            FileSystem.SYSTEM.copy(
                source = corruptedFilePath,
                target = backupPath,
            )

            AppSettings(
                backupFilename = backupFilename,
            )
        },
    )
}
