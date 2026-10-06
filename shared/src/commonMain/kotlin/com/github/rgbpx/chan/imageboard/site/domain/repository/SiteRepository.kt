package com.github.rgbpx.chan.imageboard.site.domain.repository

import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import kotlinx.coroutines.flow.Flow

interface SiteRepository {
    val sites: Flow<List<SiteSettings>>

    suspend fun save(site: SiteSettings)

    suspend fun remove(id: SiteId)
}
