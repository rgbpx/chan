package com.github.rgbpx.chan.imageboard.site.domain.repository

import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import kotlinx.coroutines.flow.Flow

interface SiteRepository {
    val sites: Flow<Map<SiteId, SiteSettings>>

    suspend fun save(site: SiteSettings)

    suspend fun remove(id: SiteId)

    suspend fun addBoard(siteId: SiteId, board: Board)

    suspend fun removeBoard(siteId: SiteId, boardId: BoardId)
}
