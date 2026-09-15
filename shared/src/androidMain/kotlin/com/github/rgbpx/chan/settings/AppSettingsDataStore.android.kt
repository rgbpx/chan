package com.github.rgbpx.chan.settings

import android.content.Context
import androidx.datastore.core.DataStore
import okio.Path.Companion.toPath

fun createDataStore(context: Context): DataStore<AppSettings> = createDataStore(
    producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath.toPath() }
)
