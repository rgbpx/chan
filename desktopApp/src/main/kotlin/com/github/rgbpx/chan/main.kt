package com.github.rgbpx.chan

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.di.AppGraph
import dev.zacsweers.metro.createGraph
import io.github.vinceglb.filekit.FileKit

private const val APP_ID = "chan"

fun main() {
    FileKit.init(appId = APP_ID)

    application {
        Logger.withTag("App").i { "App started" }

        val appGraph = createGraph<AppGraph>()

        Window(
            onCloseRequest = ::exitApplication,
            title = "Chan",
        ) {
            App(appGraph.appSettingsRepository)
        }
    }
}
