package com.github.rgbpx.chan

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.di.AppGraph
import com.github.rgbpx.chan.settings.appMetadata
import dev.zacsweers.metro.createGraph
import io.github.vinceglb.filekit.FileKit
import java.awt.Dimension

fun main() {
    FileKit.init(appId = appMetadata.appName)

    application {
        Logger.withTag("App").i { "App started" }

        val appGraph = createGraph<AppGraph>()

        Window(
            onCloseRequest = ::exitApplication,
            title = appMetadata.appTitle,
        ) {
            val density = LocalDensity.current

            LaunchedEffect(Unit) {
                window.minimumSize = with(density) {
                    Dimension(
                        appMetadata.minDefaultWidth.dp.roundToPx(),
                        appMetadata.minDefaultHeight.dp.roundToPx()
                    )
                }
            }

            App(appGraph.appSettingsRepository)
        }
    }
}
