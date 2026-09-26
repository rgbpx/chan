package com.github.rgbpx.chan.app.di

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.feature.settings.data.AppSettingsRepositoryImpl
import com.github.rgbpx.chan.feature.settings.data.createDataStore
import com.github.rgbpx.chan.feature.settings.domain.AppSettings
import com.github.rgbpx.chan.feature.settings.domain.AppSettingsRepository
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(AppScope::class)
interface AppGraph {
    val appSettingsRepository: AppSettingsRepository

    @Suppress("unused")
    @Binds
    val AppSettingsRepositoryImpl.bind: AppSettingsRepository

    @Suppress("unused")
    @Provides
    fun provideDataStore(): DataStore<AppSettings> = createDataStore()
}
