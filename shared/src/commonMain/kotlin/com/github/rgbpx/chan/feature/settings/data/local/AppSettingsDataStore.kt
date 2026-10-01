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

internal fun createDataStore(fileName: String = appMetadata.settingsFileName): DataStore<AppSettings> =
    DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = AppSettingsSerializer,
            producePath = {
                FileKit
                    .filesDir
                    .resolve(fileName)
                    .path
                    .toPath()
            },
        ),
        corruptionHandler = ReplaceFileCorruptionHandler {
            val unixTimeMs = Clock.System.now().toEpochMilliseconds()
            val backupFilename = "$fileName.backup-$unixTimeMs"

            val corruptedFilePath = FileKit
                .filesDir
                .resolve(fileName)
                .path
                .toPath()

            val backupPath = FileKit
                .filesDir
                .resolve(backupFilename)
                .path
                .toPath()

            FileSystem.SYSTEM.copy(
                source = corruptedFilePath,
                target = backupPath,
            )

            AppSettings(
                backupFilename = backupFilename,
            )
        },
    )
