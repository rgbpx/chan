package com.github.rgbpx.chan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.rgbpx.chan.settings.createDataStore
import com.github.rgbpx.chan.settings.AppSettingsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(AppContainer(applicationContext))
        }
    }
}
