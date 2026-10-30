package com.github.rgbpx.chan.imageboard.engine.fourchan

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.board.data.BoardCache
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngineFactory
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient

@ContributesIntoSet(AppScope::class)
@Inject
internal class FourChanEngineFactory(
    private val httpClient: HttpClient,
    private val boardCache: BoardCache,
) : ImageboardEngineFactory {

    override val id = FourChanImageboardEngine.ID

    override fun create(settings: SiteSettings): ImageboardEngine {
        return FourChanImageboardEngine(
            httpClient = httpClient,
            endpoints = FourChanEndpoints(settings.baseUrl),
            boardCache = boardCache,
            boardCacheKey = settings.id.value,
        )
    }
}
