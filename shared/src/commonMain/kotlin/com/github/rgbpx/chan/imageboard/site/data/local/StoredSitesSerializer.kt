package com.github.rgbpx.chan.imageboard.site.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource

internal object StoredSitesSerializer : OkioSerializer<StoredSites> {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    override val defaultValue: StoredSites = StoredSites()

    override suspend fun readFrom(source: BufferedSource): StoredSites =
        try {
            json.decodeFromString(source.readUtf8())
        } catch (exception: SerializationException) {
            throw CorruptionException(
                message = "Failed to deserialize stored sites",
                cause = exception,
            )
        }

    override suspend fun writeTo(t: StoredSites, sink: BufferedSink) {
        sink.writeUtf8(json.encodeToString(t))
    }
}
