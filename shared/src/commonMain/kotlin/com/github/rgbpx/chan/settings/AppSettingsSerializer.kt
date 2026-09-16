package com.github.rgbpx.chan.settings

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

internal object AppSettingsSerializer : OkioSerializer<AppSettings> {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override val defaultValue: AppSettings = AppSettings()

    override suspend fun readFrom(source: BufferedSource): AppSettings =
        json.decodeFromString(source.readUtf8())

    override suspend fun writeTo(t: AppSettings, sink: BufferedSink) {
        sink.writeUtf8(json.encodeToString(t))
    }
}
