package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.engine.CommonImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.EngineId
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class MakabaImageboardEngine(
    httpClient: HttpClient,
    endpoints: MakabaEndpoints,
) : CommonImageboardEngine(httpClient, endpoints) {

    override val id: EngineId = ID

    override suspend fun loadBoards(): List<Board> {
        val dtos: List<MakabaBoardDto> = httpClient
            .get(endpoints.boards())
            .body()

        return dtos.map { dto -> dto.toBoard() }
    }

    companion object {
        val ID = EngineId("makaba")
    }
}
