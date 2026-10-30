package com.github.rgbpx.chan.imageboard.engine.fourchan

import com.github.rgbpx.chan.imageboard.engine.ImageboardEndpoints

internal class FourChanEndpoints(baseUrl: String) : ImageboardEndpoints {
    private val root = baseUrl.trimEnd('/')

    override fun boards(): String = "$root/boards.json"

    override fun catalog(boardId: String): String = "$root/$boardId/catalog.json"
}
