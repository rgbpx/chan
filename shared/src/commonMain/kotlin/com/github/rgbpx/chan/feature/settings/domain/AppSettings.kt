package com.github.rgbpx.chan.feature.settings.domain

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val firstLaunch: Boolean = true,
    val corruptedBackupFileName: String? = null,
)
