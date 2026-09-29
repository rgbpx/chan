package com.github.rgbpx.chan

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.app.App
import com.github.rgbpx.chan.feature.settings.data.appMetadata
import io.github.vinceglb.filekit.FileKit
import java.awt.Dimension

fun main() {
    FileKit.init(appId = appMetadata.appName)

    application {
        Logger.withTag("App").i { "App started" }

        val windowState = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
        )

        Window(
            onCloseRequest = ::exitApplication,
            title = appMetadata.appTitle,
            state = windowState,
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

            App(
                onExitRequest = ::exitApplication,
            )
        }
    }
}
