package com.github.rgbpx.chan.imageboard.engine.fourchan

import com.github.rgbpx.chan.imageboard.board.data.BoardCache
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.engine.CommonImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.github.rgbpx.chan.imageboard.thread.domain.model.Thread
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class FourChanImageboardEngine(
    httpClient: HttpClient,
    endpoints: FourChanEndpoints,
    boardCache: BoardCache,
    boardCacheKey: String,
) : CommonImageboardEngine(httpClient, endpoints, boardCache, boardCacheKey) {

    override val id: EngineId = ID

    override suspend fun loadRemoteBoards(): List<Board> {
        val response: FourChanBoardsDto = httpClient
            .get(endpoints.boards())
            .body()

        return response.boards.map { dto -> dto.toBoard() }
    }

    override suspend fun loadRemoteCatalog(boardId: String): List<Thread> {
        TODO("step 3: catalog")
    }

    companion object {
        val ID = EngineId("fourchan")
    }
}
