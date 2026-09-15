package com.github.rgbpx.chan.settings

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow

class AppSettingsRepository(
    private val dataStore: DataStore<AppSettings>,
) {
    val settings: Flow<AppSettings> = dataStore.data

    suspend fun setFirstLaunchCompleted() {
        dataStore.updateData { it.copy(firstLaunch = false) }
    }
}
