package com.github.rgbpx.chan.settings

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import okio.FileSystem
import okio.Path

internal const val APP_NAME = "chan"
internal const val DATA_STORE_FILE_NAME = "settings.json"

internal fun createDataStore(producePath: () -> Path): DataStore<AppSettings> =
    DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = AppSettingsSerializer,
            producePath = producePath,
        )
    )
