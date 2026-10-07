package com.github.rgbpx.chan.imageboard.engine

import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings

interface ImageboardEngineFactory {
    val id: EngineId

    fun create(settings: SiteSettings): ImageboardEngine
}
