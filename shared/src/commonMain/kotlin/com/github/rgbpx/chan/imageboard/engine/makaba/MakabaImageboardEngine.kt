package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.imageboard.board.data.BoardCache
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.engine.CommonImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.github.rgbpx.chan.imageboard.thread.domain.model.Thread
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class MakabaImageboardEngine(
    httpClient: HttpClient,
    endpoints: MakabaEndpoints,
    boardCache: BoardCache,
    boardCacheKey: String,
) : CommonImageboardEngine(httpClient, endpoints, boardCache, boardCacheKey) {

    override val id: EngineId = ID

    override suspend fun loadRemoteBoards(): List<Board> {
        val dtos: List<MakabaBoardDto> = httpClient
            .get(endpoints.boards())
            .body()

        return dtos.map { dto -> dto.toBoard() }
    }

    override suspend fun loadRemoteCatalog(boardId: String): List<Thread> {
        val catalog: MakabaCatalogDto = httpClient
            .get(endpoints.catalog(boardId))
            .body()

        return catalog.threads.map { dto -> dto.toThread() }
    }

    companion object {
        val ID = EngineId("makaba")
    }
}
