package com.github.rgbpx.chan

import android.content.Context
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.settings.createDataStore

actual class AppContainer(context: Context) {
    actual val appSettingsRepository: AppSettingsRepository by lazy {
        AppSettingsRepository(createDataStore(context))
    }
}
