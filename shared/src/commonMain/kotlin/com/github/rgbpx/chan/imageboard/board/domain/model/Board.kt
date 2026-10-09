package com.github.rgbpx.chan.imageboard.board.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Board(
    val id: BoardId,
    val name: String,
) {
    val displayId: String
        get() = "/${id.value}/"
}
