package com.github.rgbpx.chan.imageboard.site.domain.usecase

import com.github.rgbpx.chan.imageboard.board.data.BoardCache
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import dev.zacsweers.metro.Inject

@Inject
class DeleteSiteUseCase(
    private val siteRepository: SiteRepository,
    private val boardCache: BoardCache,
) {
    suspend operator fun invoke(siteId: SiteId) {
        boardCache.remove(siteId.value)

        siteRepository.remove(siteId)
    }
}
