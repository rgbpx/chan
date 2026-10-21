package com.github.rgbpx.chan.imageboard.board.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.github.rgbpx.chan.imageboard.board.domain.usecase.RemoveStaleBoardsUseCase
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngineFactory
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.slack.circuit.retained.produceRetainedState
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CancellationException

@Inject
class RemoteBoardsStateProducer(
    private val removeStaleBoards: RemoveStaleBoardsUseCase,
) {
    @Composable
    fun produce(
        factory: ImageboardEngineFactory,
        site: SiteSettings,
        reloadKey: Int,
    ): BoardListScreen.RemoteBoards {
        val engine = remember(
            factory,
            site.baseUrl,
            site.engineId
        ) {
            factory.create(site)
        }

        val remoteBoardsState by produceRetainedState<BoardListScreen.RemoteBoards>(
            initialValue = BoardListScreen.RemoteBoards.Loading,
            engine,
            reloadKey,
        ) {
            value = BoardListScreen.RemoteBoards.Loading

            val loadedOrErrorState = loadRemoteBoards(engine)
            value = loadedOrErrorState

            // Get remote boards from loaded state
            val remoteBoards = when (loadedOrErrorState) {
                is BoardListScreen.RemoteBoards.Loaded -> loadedOrErrorState.boards
                else -> emptyList()
            }

            removeStaleBoards(site.id, site.boards.keys, remoteBoards)
        }

        return remoteBoardsState
    }

    private suspend fun loadRemoteBoards(
        engine: ImageboardEngine,
    ): BoardListScreen.RemoteBoards {
        return try {
            BoardListScreen.RemoteBoards.Loaded(engine.loadBoards())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            when (val message = e.message) {
                null -> BoardListScreen.RemoteBoards.Error(e.toString())
                else -> BoardListScreen.RemoteBoards.Error(message)
            }
        }
    }
}
