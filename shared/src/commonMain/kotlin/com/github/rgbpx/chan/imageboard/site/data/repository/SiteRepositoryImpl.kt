package com.github.rgbpx.chan.imageboard.site.data.repository

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import com.github.rgbpx.chan.imageboard.site.data.local.StoredSites
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SingleIn(AppScope::class)
@Inject
internal class SiteRepositoryImpl(
    private val dataStore: DataStore<StoredSites>,
) : SiteRepository {

    override val sites: Flow<Map<SiteId, SiteSettings>> = dataStore.data.map { stored ->
        stored.sites
    }

    override suspend fun save(site: SiteSettings) {
        dataStore.updateData { stored ->
            stored.copy(sites = stored.sites + (site.id to site))
        }
    }

    override suspend fun remove(id: SiteId) {
        dataStore.updateData { stored ->
            stored.copy(sites = stored.sites - id)
        }
    }

    override suspend fun addBoard(siteId: SiteId, board: Board) {
        updateSite(siteId) { site ->
            when {
                site.boards.any { it.id == board.id } -> site

                else -> site.copy(
                    boards = site.boards + board
                )
            }
        }
    }

    override suspend fun removeBoard(siteId: SiteId, boardId: BoardId) {
        updateSite(siteId) { site ->
            site.copy(
                boards = site.boards.filter {
                    it.id != boardId
                }
            )
        }
    }

    private suspend fun updateSite(
        id: SiteId,
        transform: (SiteSettings) -> SiteSettings,
    ) {
        dataStore.updateData { stored ->
            when (val site = stored.sites[id]) {
                null -> stored

                else -> stored.copy(
                    sites = stored.sites + (id to transform(site))
                )
            }
        }
    }
}
