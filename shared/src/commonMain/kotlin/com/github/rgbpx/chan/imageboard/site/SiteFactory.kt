package com.github.rgbpx.chan.imageboard.site

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.makaba.MakabaEndpoints
import com.github.rgbpx.chan.imageboard.engine.makaba.MakabaImageboardEngine
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient

@SingleIn(AppScope::class)
@Inject
class SiteFactory(
    private val httpClient: HttpClient,
) {
    fun create(config: SiteConfig): Site {
        val engine = when (config.engineId) {
            MakabaImageboardEngine.ID -> MakabaImageboardEngine(
                httpClient = httpClient,
                endpoints = MakabaEndpoints(config.baseUrl),
            )

            else -> error("Unknown engine: ${config.engineId}")
        }
        
        return Site(config, engine)
    }
}
