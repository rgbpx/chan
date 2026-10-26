package com.github.rgbpx.chan.imageboard.catalog.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineRegistry
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class CatalogPresenter(
    @Assisted private val screen: CatalogScreen,
    private val siteRepository: SiteRepository,
    private val engineRegistry: EngineRegistry,
    private val threadsStateProducer: ThreadsStateProducer,
) : Presenter<CatalogScreen.State> {

    @CircuitInject(CatalogScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted screen: CatalogScreen): CatalogPresenter
    }

    @Composable
    override fun present(): CatalogScreen.State {
        val sites by produceAndCollectAsRetainedState(initial = null) {
            siteRepository.sites
        }

        val content = when (val sitesMap = sites) {
            null -> CatalogScreen.Content.Loading
            else -> when (val siteSettings = sitesMap[screen.siteId]) {
                null -> CatalogScreen.Content.SiteNotFound
                else -> when (val engineFactory = engineRegistry.get(siteSettings.engineId)) {
                    null -> CatalogScreen.Content.UnknownEngine
                    else -> when (val board = siteSettings.boards[screen.boardId]) {
                        null -> CatalogScreen.Content.BoardNotFound
                        else -> CatalogScreen.Content.Loaded(
                            boardName = board.name,
                            threads = threadsStateProducer.produce(
                                engineFactory,
                                siteSettings,
                                screen.boardId,
                            ),
                        )
                    }
                }
            }
        }

        return CatalogScreen.State(content = content)
    }
}
