package com.github.rgbpx.chan.feature.settings.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import com.github.rgbpx.chan.feature.settings.domain.model.AppSettings
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

internal object AppSettingsSerializer : OkioSerializer<AppSettings> {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override val defaultValue: AppSettings = AppSettings()

    override suspend fun readFrom(source: BufferedSource): AppSettings =
        try {
            json.decodeFromString(source.readUtf8())
        } catch (exception: SerializationException) {
            throw CorruptionException(
                message = "Failed to deserialize app settings",
                cause = exception,
            )
        }

    override suspend fun writeTo(t: AppSettings, sink: BufferedSink) {
        sink.writeUtf8(json.encodeToString(t))
    }
}
