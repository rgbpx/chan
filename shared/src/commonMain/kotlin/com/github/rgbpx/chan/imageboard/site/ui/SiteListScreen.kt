package com.github.rgbpx.chan.imageboard.site.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data object SiteListScreen : Screen {
    sealed interface State : CircuitUiState {
        data object Loading : State

        data class Loaded(
            val siteIds: List<SiteId>,
            val eventSink: (Event) -> Unit,
        ) : State
    }

    sealed interface Event : CircuitUiEvent {
        data class SiteClicked(
            val id: SiteId,
        ) : Event
    }
}
