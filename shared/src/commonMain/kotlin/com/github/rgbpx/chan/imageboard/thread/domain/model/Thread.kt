package com.github.rgbpx.chan.imageboard.thread.domain.model

data class Thread(
    val id: ThreadId,
    val subject: String,
    val comment: String,
    val postsCount: Int,
)
