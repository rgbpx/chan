package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.imageboard.engine.CommonImageboardEngine
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.github.rgbpx.chan.imageboard.model.Board
import com.github.rgbpx.chan.imageboard.site.Site
import io.ktor.client.HttpClient

class MakabaImageboardEngine(
    httpClient: HttpClient,
) : CommonImageboardEngine(httpClient) {

    override val id: EngineId = EngineId("makaba")

    override suspend fun loadBoards(site: Site): List<Board> {
        TODO()
    }
}
