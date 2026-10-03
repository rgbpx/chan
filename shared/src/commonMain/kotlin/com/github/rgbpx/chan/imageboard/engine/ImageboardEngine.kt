package com.github.rgbpx.chan.imageboard.engine

import com.github.rgbpx.chan.imageboard.model.Board
import com.github.rgbpx.chan.imageboard.site.Site

interface ImageboardEngine {
    val id: EngineId
    suspend fun loadBoards(site: Site): List<Board>
}
