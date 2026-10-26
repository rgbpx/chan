package com.github.rgbpx.chan.imageboard.engine

import com.github.rgbpx.chan.imageboard.board.data.BoardCache
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.thread.domain.model.Thread
import io.ktor.client.HttpClient

abstract class CommonImageboardEngine(
    httpClient: HttpClient,
    protected val endpoints: ImageboardEndpoints,
    private val boardCache: BoardCache,
    private val boardCacheKey: String,
) : BaseImageboardEngine(httpClient) {

    protected abstract suspend fun loadRemoteBoards(): List<Board>

    protected abstract suspend fun loadRemoteCatalog(boardId: String): List<Thread>

    override suspend fun loadBoards(force: Boolean): List<Board> {
        return when (force) {
            true -> loadAndCacheBoards()

            else -> {
                val cached = loadCachedBoards()

                when (cached.isEmpty()) {
                    true -> loadAndCacheBoards()

                    else -> cached
                }
            }
        }
    }

    override suspend fun loadCatalog(boardId: String): List<Thread> {
        return loadRemoteCatalog(boardId)
    }

    private suspend fun loadAndCacheBoards(): List<Board> {
        val boards = loadRemoteBoards()
        cacheBoards(boards)

        return boards
    }

    private fun cacheBoards(boards: List<Board>) {
        when (boards.isEmpty()) {
            true -> boardCache.remove(boardCacheKey)
            false -> boardCache.put(boardCacheKey, boards)
        }
    }

    private fun loadCachedBoards(): List<Board> {
        return when (val boards = boardCache.get(boardCacheKey)) {
            null -> emptyList()
            else -> boards
        }
    }
}
