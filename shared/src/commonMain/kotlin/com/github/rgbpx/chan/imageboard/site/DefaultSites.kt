package com.github.rgbpx.chan.imageboard.site

import com.github.rgbpx.chan.imageboard.engine.makaba.MakabaImageboardEngine
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings

object DefaultSites {
    val all = mapOf(
        SiteId("2ch") to SiteSettings(
            id = SiteId("2ch"),
            name = "2ch",
            baseUrl = "https://2ch.su",
            engineId = MakabaImageboardEngine.ID,
        ),
    )
}
