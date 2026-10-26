package com.github.rgbpx.chan.imageboard.engine

import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.thread.domain.model.Thread

interface ImageboardEngine {
    val id: EngineId
    suspend fun loadBoards(force: Boolean = false): List<Board>
    suspend fun loadCatalog(boardId: String): List<Thread>
}
