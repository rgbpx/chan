package com.github.rgbpx.chan.imageboard.site.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data class SiteSettingsScreen(
    val engineId: EngineId,
) : Screen {
    sealed interface State : CircuitUiState {
        data object Loading : State

        data class Loaded(
            val engines: List<EngineId>,
            val currentEngineId: EngineId,
            val eventSink: (Event) -> Unit,
        ) : State
    }

    sealed interface Event : CircuitUiEvent {
        data class EngineSelected(
            val engine: EngineId,
        ) : Event
    }
}
