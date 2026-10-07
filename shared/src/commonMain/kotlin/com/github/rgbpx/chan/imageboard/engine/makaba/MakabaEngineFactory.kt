package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.ImageboardEngineFactory
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient

@ContributesIntoSet(AppScope::class)
@Inject
internal class MakabaEngineFactory(
    private val httpClient: HttpClient,
) : ImageboardEngineFactory {

    override val id = MakabaImageboardEngine.ID

    override fun create(settings: SiteSettings): ImageboardEngine {
        return MakabaImageboardEngine(
            httpClient = httpClient,
            endpoints = MakabaEndpoints(settings.baseUrl),
        )
    }
}
