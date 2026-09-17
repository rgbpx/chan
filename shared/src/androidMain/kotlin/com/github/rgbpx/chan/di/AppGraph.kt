package com.github.rgbpx.chan.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.settings.AppSettings
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.settings.AppSettingsRepositoryImpl
import com.github.rgbpx.chan.settings.createDataStore
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
    fun provideDataStore(context: Context): DataStore<AppSettings> = createDataStore(context)

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides context: Context): AppGraph
    }
}
