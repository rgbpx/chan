package com.github.rgbpx.chan.di

import android.content.Context
import androidx.datastore.core.DataStore
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Binds
import com.github.rgbpx.chan.settings.AppSettings
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.settings.AppSettingsRepositoryImpl
import com.github.rgbpx.chan.settings.createDataStore

@DependencyGraph(AppScope::class)
interface AppGraph {
    val appSettingsRepository: AppSettingsRepository

    @Binds
    val AppSettingsRepositoryImpl.bind: AppSettingsRepository

    @Provides
    fun provideDataStore(context: Context): DataStore<AppSettings> = createDataStore(context)

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides context: Context): AppGraph
    }
}
