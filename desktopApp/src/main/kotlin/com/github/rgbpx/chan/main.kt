package com.github.rgbpx.chan

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import androidx.compose.ui.window.v2.Window
import androidx.compose.ui.window.v2.WindowBoundsProvider
import androidx.compose.ui.window.v2.WindowPositionProvider
import androidx.compose.ui.window.v2.WindowSizeProvider
import androidx.compose.ui.window.v2.rememberWindowState
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.app.data.appMetadata
import com.github.rgbpx.chan.app.ui.App
import io.github.vinceglb.filekit.FileKit


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    FileKit.init(appId = appMetadata.appName)

    application {
        Logger.withTag("App").i { "App started" }

        val windowState = rememberWindowState(
            initialBoundsProvider = WindowBoundsProvider(
                positionProvider = WindowPositionProvider.CenteredOnScreen,
                sizeProvider = WindowSizeProvider.Default,
            ),
        )

        Window(
            onCloseRequest = ::exitApplication,
            title = appMetadata.appTitle,
            state = windowState,
            minSize = DpSize(
                width = appMetadata.minDefaultWidth.dp,
                height = appMetadata.minDefaultHeight.dp,
            ),
        ) {
            App(
                onExitRequest = ::exitApplication,
            )
        }
    }
}
