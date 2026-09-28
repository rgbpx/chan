package com.github.rgbpx.chan.feature.recovery

import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data class RecoveryScreen(val corruptedBackupFileName: String) : Screen {
    data class State(
        val copyContent: String? = null,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data object CopyClicked : Event
        data object ResetClicked : Event
    }
}
