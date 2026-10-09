package com.github.rgbpx.chan.imageboard.site.domain.model

import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.engine.EngineId
import kotlinx.serialization.Serializable

@Serializable
data class SiteSettings(
    val id: SiteId,
    val name: String,
    val baseUrl: String,
    val engineId: EngineId,
    val boards: List<Board> = emptyList(),
)
