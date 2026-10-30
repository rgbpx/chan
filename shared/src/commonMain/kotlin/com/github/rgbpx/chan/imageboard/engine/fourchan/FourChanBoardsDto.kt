package com.github.rgbpx.chan.imageboard.engine.fourchan

import kotlinx.serialization.Serializable

@Serializable
internal data class FourChanBoardsDto(
    val boards: List<FourChanBoardDto>,
)
