package com.github.rgbpx.chan.imageboard.engine.makaba

internal class MakabaEndpoints(baseUrl: String) {
    private val root = baseUrl.trimEnd('/')

    fun boards(): String = "$root/api/mobile/v2/boards"
}
