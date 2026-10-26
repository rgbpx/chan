package com.github.rgbpx.chan.imageboard.engine.makaba

import com.github.rgbpx.chan.imageboard.thread.domain.model.Thread
import com.github.rgbpx.chan.imageboard.thread.domain.model.ThreadId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MakabaThreadDto(
    val num: Long,
    val subject: String = "",
    val comment: String = "",
    @SerialName("posts_count") val postsCount: Int = 0,
)

internal fun MakabaThreadDto.toThread(): Thread = Thread(
    id = ThreadId(num),
    subject = subject,
    comment = comment,
    postsCount = postsCount,
)
