package com.github.rgbpx.chan.imageboard.site.data.repository

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.site.data.local.StoredSites
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@SingleIn(AppScope::class)
@Inject
internal class SiteRepositoryImpl(
    private val dataStore: DataStore<StoredSites>,
) : SiteRepository {

    override val sites: Flow<List<SiteSettings>> = dataStore.data.map { stored ->
        stored.sites
    }

    override suspend fun add(site: SiteSettings) {
        dataStore.updateData { stored ->
            require(stored.sites.none { it.id == site.id }) {
                "Site ${site.id} already exists"
            }

            stored.copy(sites = stored.sites + site)
        }
    }

    override suspend fun update(site: SiteSettings) {
        dataStore.updateData { stored ->
            require(stored.sites.any { it.id == site.id }) {
                "Site ${site.id} not found"
            }

            stored.copy(
                sites = stored.sites.map { existing ->
                    if (existing.id == site.id) site else existing
                },
            )
        }
    }

    override suspend fun remove(id: SiteId) {
        dataStore.updateData { stored ->
            stored.copy(sites = stored.sites.filterNot { it.id == id })
        }
    }
}
