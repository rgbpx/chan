package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.imageboard.engine.CommonImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.github.rgbpx.chan.imageboard.model.Board
import io.ktor.client.HttpClient

class MakabaImageboardEngine(
    httpClient: HttpClient,
) : CommonImageboardEngine(httpClient) {

    override val id: EngineId = EngineId("makaba")

    override suspend fun loadBoards(): List<Board> {
        TODO()
    }
}
