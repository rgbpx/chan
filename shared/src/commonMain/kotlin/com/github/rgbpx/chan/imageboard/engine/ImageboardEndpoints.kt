package com.github.rgbpx.chan.imageboard.engine

interface ImageboardEndpoints {
    fun boards(): String

    fun catalog(boardId: String): String
}
