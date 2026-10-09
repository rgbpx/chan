package com.github.rgbpx.chan.imageboard.board.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineRegistry
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngine
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

                    else -> presentBoards(engineFactory, siteSettings, reloadKey)
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
): BoardListScreen.Content {
    val engine = remember(factory, site) {
        factory.create(site)
    }

    val content by produceRetainedState<BoardListScreen.Content>(
        initialValue = BoardListScreen.Content.Loading,
        engine,
        reloadKey,
    ) {
        value = BoardListScreen.Content.Loading
        value = loadBoards(engine)
    }

    return content
}

private suspend fun loadBoards(engine: ImageboardEngine): BoardListScreen.Content {
    return try {
        BoardListScreen.Content.Loaded(engine.loadBoards())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        when (val message = e.message) {
            null -> BoardListScreen.Content.Error(e.toString())
            else -> BoardListScreen.Content.Error(message)
        }
    }
}
