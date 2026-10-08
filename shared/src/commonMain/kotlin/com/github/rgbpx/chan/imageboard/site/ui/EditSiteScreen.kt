package com.github.rgbpx.chan.imageboard.site.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data class EditSiteScreen(
    val siteId: SiteId,
) : Screen {
    sealed interface State : CircuitUiState {
        data object Loading : State

        data object NotFound : State

        data class Loaded(
            val name: String,
            val baseUrl: String,
            val engineId: EngineId,
            val engineIds: List<EngineId>,
            val eventSink: (Event) -> Unit,
        ) : State
    }

    sealed interface Event : CircuitUiEvent {
        data class NameChanged(val name: String) : Event

        data class BaseUrlChanged(val baseUrl: String) : Event

        data class EngineSelected(val engineId: EngineId) : Event

        data object SaveClicked : Event
    }
}
