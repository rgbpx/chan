package com.github.rgbpx.chan.imageboard.engine.makaba

import kotlinx.serialization.Serializable

@Serializable
internal data class MakabaCatalogDto(
    val threads: List<MakabaThreadDto> = emptyList(),
)
