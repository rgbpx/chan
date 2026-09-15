package com.github.rgbpx.chan

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.zacsweers.metro.createGraph
import com.github.rgbpx.chan.di.AppGraph

fun main() = application {
    val appGraph = createGraph<AppGraph>()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Chan",
    ) {
        App(appGraph.appSettingsRepository)
    }
}
