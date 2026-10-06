package com.github.rgbpx.chan.imageboard.site.data.local

import com.github.rgbpx.chan.imageboard.site.DefaultSites
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import kotlinx.serialization.Serializable

@Serializable
internal data class StoredSites(
    val sites: Map<SiteId, SiteSettings> = DefaultSites.all,
)
