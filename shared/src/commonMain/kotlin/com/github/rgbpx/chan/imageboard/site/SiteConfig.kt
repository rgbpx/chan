package com.github.rgbpx.chan.imageboard.site

import com.github.rgbpx.chan.imageboard.engine.EngineId

data class SiteConfig(
    val id: SiteId,
    val name: String,
    val baseUrl: String,
    val engineId: EngineId,
)
