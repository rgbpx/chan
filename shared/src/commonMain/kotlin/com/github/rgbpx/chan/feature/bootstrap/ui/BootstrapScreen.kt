package com.github.rgbpx.chan.feature.bootstrap.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data object BootstrapScreen : Screen {
    data object State : CircuitUiState
}
