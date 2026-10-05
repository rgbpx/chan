package com.github.rgbpx.chan.imageboard.site.di

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.site.data.local.StoredSites
import com.github.rgbpx.chan.imageboard.site.data.local.createSitesDataStore
import com.github.rgbpx.chan.imageboard.site.data.repository.SiteRepositoryImpl
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn

@BindingContainer
internal object SiteBindings {
    @Provides
    @SingleIn(AppScope::class)
    fun provideSitesDataStore(): DataStore<StoredSites> = createSitesDataStore()

    @Provides
    fun provideSiteRepository(impl: SiteRepositoryImpl): SiteRepository = impl
}
