package com.github.rgbpx.chan

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.rgbpx.chan.settings.createDataStore
import com.github.rgbpx.chan.settings.AppSettingsRepository

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Chan",
    ) {
        App(AppContainer())
    }
}
