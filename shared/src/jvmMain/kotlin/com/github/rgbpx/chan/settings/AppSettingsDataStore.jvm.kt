package com.github.rgbpx.chan.settings

import androidx.datastore.core.DataStore
import okio.FileSystem
import okio.Path.Companion.toPath

fun createDataStore(): DataStore<AppSettings> = createDataStore(
    producePath = {
        val homeDir = System.getenv("HOME")
        val configDir = System.getenv("XDG_CONFIG_HOME")
            ?: "$homeDir/.config"
        val baseDir = System.getenv("APPDATA") ?: configDir
        val dir = "$baseDir/$APP_NAME".toPath()

        FileSystem.SYSTEM.createDirectories(dir)

        dir / DATA_STORE_FILE_NAME
    }
)
