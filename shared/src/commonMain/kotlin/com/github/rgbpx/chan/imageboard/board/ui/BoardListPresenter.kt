package com.github.rgbpx.chan.imageboard.board.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineRegistry
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngineFactory
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.retained.produceRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@AssistedInject
class BoardListPresenter(
    @Assisted private val screen: BoardListScreen,
    @Assisted private val navigator: Navigator,
    private val siteRepository: SiteRepository,
    private val engineRegistry: EngineRegistry,
) : Presenter<BoardListScreen.State> {

    @CircuitInject(BoardListScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            @Assisted screen: BoardListScreen,
            @Assisted navigator: Navigator,
        ): BoardListPresenter
    }

    @Composable
    override fun present(): BoardListScreen.State {
        val scope = rememberCoroutineScope()
        var reloadKey by rememberRetained { mutableIntStateOf(0) }

        val sites by produceAndCollectAsRetainedState(
            initial = null,
        ) {
            siteRepository.sites
        }

        val content = when (val sitesMap = sites) {
            null -> BoardListScreen.Content.Loading

            else -> when (val siteSettings = sitesMap[screen.siteId]) {
                null -> BoardListScreen.Content.SiteNotFound

                else -> when (val engineFactory = engineRegistry.get(siteSettings.engineId)) {
                    null -> BoardListScreen.Content.UnknownEngine

                    else -> presentBoards(engineFactory, siteSettings, reloadKey, siteRepository)
                }
            }
        }

        return BoardListScreen.State(
            content = content,
            eventSink = { event ->
                when (event) {
                    BoardListScreen.Event.BackClicked -> {
                        navigator.pop()
                    }

                    BoardListScreen.Event.RefreshClicked -> {
                        reloadKey += 1
                    }

                    is BoardListScreen.Event.AddBoardClicked -> {
                        scope.launch {
                            siteRepository.addBoard(
                                screen.siteId,
                                event.board,
                            )
                        }
                    }

                    is BoardListScreen.Event.RemoveBoardClicked -> {
                        scope.launch {
                            siteRepository.removeBoard(
                                screen.siteId,
                                event.boardId,
                            )
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun presentBoards(
    factory: ImageboardEngineFactory,
    site: SiteSettings,
    reloadKey: Int,
    siteRepository: SiteRepository,
): BoardListScreen.Content.Loaded {
    val engine = remember(factory, site.baseUrl, site.engineId) {
        factory.create(site)
    }

    val loadResult by produceRetainedState<BoardListScreen.AvailableBoards>(
        initialValue = BoardListScreen.AvailableBoards.Loading,
        engine,
        reloadKey,
    ) {
        value = BoardListScreen.AvailableBoards.Loading

        val result = try {
            BoardListScreen.AvailableBoards.Loaded(engine.loadBoards())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            when (val message = e.message) {
                null -> BoardListScreen.AvailableBoards.Error(e.toString())
                else -> BoardListScreen.AvailableBoards.Error(message)
            }
        }

        value = result

        if (result is BoardListScreen.AvailableBoards.Loaded && result.boards.isNotEmpty()) {
            val loadedIds = result.boards.map { board -> board.id }.toSet()
            val missingIds = site.boards.keys - loadedIds

            if (missingIds.isNotEmpty()) {
                siteRepository.removeBoards(site.id, missingIds)
            }
        }
    }

    val available = when (val result = loadResult) {
        is BoardListScreen.AvailableBoards.Loaded -> {
            BoardListScreen.AvailableBoards.Loaded(
                result.boards.filter { board ->
                    board.id !in site.boards
                }
            )
        }

        else -> result
    }

    return BoardListScreen.Content.Loaded(
        added = site.boards.values.toList(),
        available = available,
    )
}
