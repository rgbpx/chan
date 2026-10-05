package com.github.rgbpx.chan.imageboard.engine

import com.github.rgbpx.chan.imageboard.model.Board

interface ImageboardEngine {
    val id: EngineId
    suspend fun loadBoards(): List<Board>
}
