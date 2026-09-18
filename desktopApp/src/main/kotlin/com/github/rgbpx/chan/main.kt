package com.github.rgbpx.chan

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.di.AppGraph
import com.github.rgbpx.chan.settings.appMetadata
import dev.zacsweers.metro.createGraph
import io.github.vinceglb.filekit.FileKit

fun main() {
    FileKit.init(appId = appMetadata.appName)

    application {
        Logger.withTag("App").i { "App started" }

        val appGraph = createGraph<AppGraph>()

        Window(
            onCloseRequest = ::exitApplication,
            title = appMetadata.appTitle,
        ) {
            App(appGraph.appSettingsRepository)
        }
    }
}
