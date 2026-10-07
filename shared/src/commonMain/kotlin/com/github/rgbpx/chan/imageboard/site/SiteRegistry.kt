package com.github.rgbpx.chan.imageboard.site

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineRegistry
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient

@SingleIn(AppScope::class)
@Inject
class SiteRegistry(
    private val httpClient: HttpClient,
    private val engineRegistry: EngineRegistry
) {
    private val sites = mutableMapOf<SiteId, Site>()

    fun load(settings: Map<SiteId, SiteSettings>) {
        sites.clear()

        settings.forEach { (id, siteSettings) ->
            sites[id] = createSite(siteSettings)
        }
    }

    fun get(id: SiteId): Site? = sites[id]

    fun all(): Collection<Site> = sites.values

    private fun createSite(settings: SiteSettings): Site {
        val engineFactory = engineRegistry.get(settings.engineId)
            ?: error("Unknown engine: ${settings.engineId}")

        val engine = engineFactory.create(settings)

        return Site(settings, engine)
    }
}
