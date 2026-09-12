package com.github.rgbpx.chan

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform