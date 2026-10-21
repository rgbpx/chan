package com.github.rgbpx.chan.imageboard.board.domain.usecase

import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import dev.zacsweers.metro.Inject

/**
 * Removes locally added boards that are no longer present on the remote source.
 * (only if remote is not empty)
 */
@Inject
class RemoveStaleBoardsUseCase(
    private val siteRepository: SiteRepository,
) {
    suspend operator fun invoke(
        siteId: SiteId,
        addedBoards: Set<BoardId>,
        remoteBoards: List<Board>,
    ) {
        when {
            remoteBoards.isNotEmpty() -> {
                val remoteBoardIds = remoteBoards.map { board -> board.id }.toSet()
                val staleBoardIds = addedBoards - remoteBoardIds

                when {
                    // TODO: Cleanup errors are not handled yet. A failed write here is not caught.
                    staleBoardIds.isNotEmpty() -> siteRepository.removeBoards(
                        siteId,
                        staleBoardIds
                    )
                }
            }
        }
    }
}
