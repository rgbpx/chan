package com.github.rgbpx.chan.settings

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val firstLaunch: Boolean = true,
)
