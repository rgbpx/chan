package com.github.rgbpx.chan.settings

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import okio.FileSystem
import okio.Path.Companion.toPath

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
        )
    )
