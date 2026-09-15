package com.github.rgbpx.chan

import com.github.rgbpx.chan.settings.AppSettingsRepository

expect class AppContainer {
    val appSettingsRepository: AppSettingsRepository
}
