package com.github.rgbpx.chan.di

import androidx.datastore.core.DataStore
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import com.github.rgbpx.chan.settings.AppSettings
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.settings.createDataStore

@DependencyGraph(AppScope::class)
interface AppGraph {
    val appSettingsRepository: AppSettingsRepository

    @Provides
    fun provideDataStore(): DataStore<AppSettings> = createDataStore()
}
