package com.github.rgbpx.chan.feature.home.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data object HomeScreen : Screen {
    data class State(
        val content: Content, // new
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    // new
    sealed interface Content {
        data object Loading : Content

        data object NoSites : Content

        data object NoBoards : Content
    }

    sealed interface Event : CircuitUiEvent {
        data object SettingsClicked : Event
    }
}
