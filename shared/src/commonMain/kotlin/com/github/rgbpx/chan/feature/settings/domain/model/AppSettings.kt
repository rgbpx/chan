package com.github.rgbpx.chan.feature.settings.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val firstLaunch: Boolean = true,
    val backupFilename: String? = null,
)
