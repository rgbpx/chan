package com.github.rgbpx.chan.imageboard.catalog.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngineFactory
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.slack.circuit.retained.produceRetainedState
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.CancellationException

@Inject
class ThreadsStateProducer {
    @Composable
    fun produce(
        factory: ImageboardEngineFactory,
        site: SiteSettings,
        boardId: BoardId,
    ): CatalogScreen.Threads {
        val engine = remember(
            factory,
            site.baseUrl,
            site.engineId,
        ) {
            factory.create(site)
        }

        val threads by produceRetainedState<CatalogScreen.Threads>(
            initialValue = CatalogScreen.Threads.Loading,
            engine,
            boardId,
        ) {
            value = CatalogScreen.Threads.Loading
            value = loadThreads(engine, boardId)
        }

        return threads
    }

    private suspend fun loadThreads(
        engine: ImageboardEngine,
        boardId: BoardId,
    ): CatalogScreen.Threads {
        return try {
            CatalogScreen.Threads.Loaded(engine.loadCatalog(boardId.value))
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            when (val message = e.message) {
                null -> CatalogScreen.Threads.Error(e.toString())
                else -> CatalogScreen.Threads.Error(message)
            }
        }
    }
}
