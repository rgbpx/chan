package com.github.rgbpx.chan.settings

import android.content.Context
import androidx.datastore.core.DataStore
import okio.Path.Companion.toPath

lateinit var appContext: Context

actual fun createDataStore(): DataStore<AppSettings> = createDataStore(
    producePath = { appContext.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath() }
)
