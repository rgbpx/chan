package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.imageboard.engine.ImageboardEndpoints

internal class MakabaEndpoints(baseUrl: String) : ImageboardEndpoints {
    private val root = baseUrl.trimEnd('/')
    private val apiBase = "$root/api/mobile/v2"

    override fun boards(): String = "$apiBase/boards"
}
