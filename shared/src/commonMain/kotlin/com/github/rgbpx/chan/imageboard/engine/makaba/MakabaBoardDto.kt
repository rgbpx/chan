package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.imageboard.model.Board
import com.github.rgbpx.chan.imageboard.model.BoardId
import kotlinx.serialization.Serializable

@Serializable
internal data class MakabaBoardDto(
    val id: String,
    val name: String,
)

internal fun MakabaBoardDto.toBoard(): Board = Board(
    id = BoardId(id),
    name = name,
)
