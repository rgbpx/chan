package com.github.rgbpx.chan.imageboard.site.ui

enum class BaseUrlError {
    InvalidScheme,
    InvalidHost,
}

fun validateBaseUrl(input: String): BaseUrlError? {
    val url = input.trim()
    if (url.isEmpty()) {
        return null
    }

    val rest = when {
        url.startsWith("https://", ignoreCase = true) -> url.substring(8)
        url.startsWith("http://", ignoreCase = true) -> url.substring(7)
        
        else -> return BaseUrlError.InvalidScheme
    }

    val host = rest.substringBefore('/')
    if (host.isEmpty() || host.any { it.isWhitespace() }) {
        return BaseUrlError.InvalidHost
    }

    return null
}
