package com.github.rgbpx.chan.imageboard.site

import com.github.rgbpx.chan.imageboard.engine.EngineId
import kotlinx.serialization.Serializable

@Serializable
data class SiteConfig(
    val id: SiteId,
    val name: String,
    val baseUrl: String,
    val engineId: EngineId,
)
