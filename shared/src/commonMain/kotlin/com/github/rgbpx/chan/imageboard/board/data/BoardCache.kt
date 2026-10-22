package com.github.rgbpx.chan.imageboard.board.data

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@Inject
class BoardCache {

    private val cache = mutableMapOf<String, List<Board>>()

    fun get(key: String): List<Board>? {
        return cache[key]
    }

    fun put(key: String, value: List<Board>) {
        cache[key] = value
    }

    fun remove(key: String) {
        cache.remove(key)
    }
}
