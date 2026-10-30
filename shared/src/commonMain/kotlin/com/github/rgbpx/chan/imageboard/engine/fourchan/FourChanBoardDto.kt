package com.github.rgbpx.chan.imageboard.engine.fourchan

import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FourChanBoardDto(
    @SerialName("board") val id: String,
    @SerialName("title") val name: String,
)

internal fun FourChanBoardDto.toBoard(): Board = Board(
    id = BoardId(id),
    name = name,
)
