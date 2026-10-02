package com.github.rgbpx.chan.core.network

import co.touchlab.kermit.Logger as KermitLogger
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val CONNECT_TIMEOUT_MS = 10_000L
private const val SOCKET_TIMEOUT_MS = 30_000L

private val networkJson = Json {
    ignoreUnknownKeys = true
}

private val httpLogger = object : Logger {
    private val kermit = KermitLogger.withTag("HttpClient")

    override fun log(message: String) = kermit.d { message }
}

internal fun createHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(networkJson)
    }

    install(HttpTimeout) {
        connectTimeoutMillis = CONNECT_TIMEOUT_MS
        socketTimeoutMillis = SOCKET_TIMEOUT_MS
    }

    install(Logging) {
        logger = httpLogger
        level = LogLevel.INFO
        sanitizeHeader { header ->
            header == HttpHeaders.Authorization ||
                    header == HttpHeaders.Cookie ||
                    header == HttpHeaders.SetCookie
        }
    }
}
