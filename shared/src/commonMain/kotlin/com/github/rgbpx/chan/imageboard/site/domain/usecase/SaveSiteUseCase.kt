package com.github.rgbpx.chan.imageboard.site.domain.usecase

import com.github.rgbpx.chan.imageboard.board.data.BoardCache
import com.github.rgbpx.chan.imageboard.engine.EngineId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import dev.zacsweers.metro.Inject

@Inject
class SaveSiteUseCase(
    private val siteRepository: SiteRepository,
    private val boardCache: BoardCache,
) {
    suspend operator fun invoke(
        site: SiteSettings,
        newName: String,
        newBaseUrl: String,
        newEngineId: EngineId,
    ) {
        val newBaseUrl = newBaseUrl.trim()
        val newBoards = when (newEngineId) {
            site.engineId -> site.boards
            else -> emptyMap()
        }

        siteRepository.save(
            site.copy(
                name = newName.trim(),
                baseUrl = newBaseUrl,
                engineId = newEngineId,
                boards = newBoards,
            ),
        )

        val engineIdChanged = newEngineId != site.engineId
        val baseUrlChanged = newBaseUrl != site.baseUrl
        val cleaBoardCache = engineIdChanged || baseUrlChanged
        if (cleaBoardCache) {
            boardCache.remove(site.id.value)
        }
    }
}
