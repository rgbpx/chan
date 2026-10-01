package com.github.rgbpx.chan.feature.recovery.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data class RecoveryScreen(val backupFilename: String) : Screen {
    sealed interface State : CircuitUiState {
        val eventSink: (Event) -> Unit

        data class Loading(
            override val eventSink: (Event) -> Unit,
        ) : State

        data class Loaded(
            override val eventSink: (Event) -> Unit,
            val backupContent: String,
        ) : State
    }

    sealed interface Event : CircuitUiEvent {
        data object Reset : Event
    }
}
