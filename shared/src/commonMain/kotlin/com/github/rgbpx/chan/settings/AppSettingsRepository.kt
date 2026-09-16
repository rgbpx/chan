package com.github.rgbpx.chan.settings

import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun setFirstLaunchCompleted()
}
