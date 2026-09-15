package com.github.rgbpx.chan

import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.settings.createDataStore

actual class AppContainer {
    actual val appSettingsRepository: AppSettingsRepository by lazy {
        AppSettingsRepository(createDataStore())
    }
}
