package com.github.rgbpx.chan.settings

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

internal object AppSettingsSerializer : OkioSerializer<AppSettings> {

    override val defaultValue: AppSettings = AppSettings()

    override suspend fun readFrom(source: BufferedSource): AppSettings =
        Json.decodeFromString(source.readUtf8())

    override suspend fun writeTo(t: AppSettings, sink: BufferedSink) {
        sink.writeUtf8(Json.encodeToString(t))
    }
}
