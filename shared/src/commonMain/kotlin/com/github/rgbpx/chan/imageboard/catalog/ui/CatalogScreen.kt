package com.github.rgbpx.chan.imageboard.catalog.ui

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.thread.domain.model.Thread
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable

@CircuitSerializable(AppScope::class)
data class CatalogScreen(
    val siteId: SiteId,
    val boardId: BoardId,
) : Screen {
    data class State(
        val content: Content,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Content {
        data object Loading : Content

        data object SiteNotFound : Content

        data object UnknownEngine : Content

        data object BoardNotFound : Content

        data class Loaded(
            val boardName: String,
            val threads: Threads,
        ) : Content
    }

    sealed interface Threads {
        data object Loading : Threads

        data class Error(val message: String) : Threads

        data class Loaded(val threads: List<Thread>) : Threads
    }

    sealed interface Event : CircuitUiEvent {
        data object SettingsClicked : Event
    }
}
