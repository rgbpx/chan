package com.github.rgbpx.chan.feature.bootstrap

import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data object BootstrapScreen : Screen {
    sealed interface State : CircuitUiState {
        data object Loading : State
        data class Error(val throwable: Throwable) : State
    }
}
