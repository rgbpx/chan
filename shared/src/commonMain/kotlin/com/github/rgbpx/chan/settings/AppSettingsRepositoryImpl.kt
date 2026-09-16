package com.github.rgbpx.chan.settings

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.di.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@Inject
class AppSettingsRepositoryImpl(
    private val dataStore: DataStore<AppSettings>,
) : AppSettingsRepository {
    override val settings = dataStore.data

    override suspend fun setFirstLaunchCompleted() {
        dataStore.updateData { it.copy(firstLaunch = false) }
    }
}
