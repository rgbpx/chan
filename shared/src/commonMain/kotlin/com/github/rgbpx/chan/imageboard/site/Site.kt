package com.github.rgbpx.chan.imageboard.site

import com.github.rgbpx.chan.imageboard.engine.ImageboardEngine
import com.github.rgbpx.chan.imageboard.model.Board

class Site(
    val settings: SiteSettings,
    private val engine: ImageboardEngine,
) {
    suspend fun loadBoards(): List<Board> = engine.loadBoards()
}
