package com.github.rgbpx.chan.imageboard.site.domain.repository

import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import kotlinx.coroutines.flow.Flow

interface SiteRepository {
    val sites: Flow<List<SiteSettings>>

    suspend fun add(site: SiteSettings)

    suspend fun update(site: SiteSettings)

    suspend fun remove(id: SiteId)
}
