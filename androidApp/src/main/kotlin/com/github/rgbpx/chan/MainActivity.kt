package com.github.rgbpx.chan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.rgbpx.chan.di.AppGraph
import dev.zacsweers.metro.createGraphFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val appGraph = createGraphFactory<AppGraph.Factory>().create(applicationContext)

        setContent {
            App(appGraph.appSettingsRepository)
        }
    }
}
