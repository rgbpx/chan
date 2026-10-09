package com.github.rgbpx.chan.imageboard.board.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data class BoardListScreen(
    val siteId: SiteId,
) : Screen {
    data class State(
        val content: Content,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Content {
        data object Loading : Content

        data object SiteNotFound : Content

        data object UnknownEngine : Content

        data class Error(val message: String) : Content

        data class Loaded(val boards: List<Board>) : Content
    }

    sealed interface Event : CircuitUiEvent {
        data object BackClicked : Event

        data object RefreshClicked : Event
    }
}
