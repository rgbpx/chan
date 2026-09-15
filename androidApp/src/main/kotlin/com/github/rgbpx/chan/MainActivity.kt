package com.github.rgbpx.chan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.rgbpx.chan.settings.createDataStore
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.settings.appContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        appContext = applicationContext

        val appSettingsRepository = AppSettingsRepository(createDataStore())

        setContent {
            App(appSettingsRepository)
        }
    }
}
