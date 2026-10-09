package com.github.rgbpx.chan.imageboard.site.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data object NewSiteScreen : Screen {
    data class State(
        val name: String,
        val baseUrl: String,
        val engineId: EngineId,
        val engineIds: List<EngineId>,
        val baseUrlError: BaseUrlError?,
        val canSave: Boolean,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class NameChanged(val name: String) : Event

        data class BaseUrlChanged(val baseUrl: String) : Event

        data class EngineSelected(val engineId: EngineId) : Event

        data object SaveClicked : Event

        data object CancelClicked : Event
    }
}
