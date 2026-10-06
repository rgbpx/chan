package com.github.rgbpx.chan.app.di

import androidx.datastore.core.DataStore
import com.github.rgbpx.chan.core.network.di.NetworkBindings
import com.github.rgbpx.chan.feature.settings.data.local.createDataStore
import com.github.rgbpx.chan.feature.settings.data.repository.AppSettingsRepositoryImpl
import com.github.rgbpx.chan.feature.settings.domain.model.AppSettings
import com.github.rgbpx.chan.feature.settings.domain.repository.AppSettingsRepository
import com.github.rgbpx.chan.imageboard.site.SiteRegistry
import com.github.rgbpx.chan.imageboard.site.di.SiteBindings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.CircuitSaver
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.serialization.CircuitSerializerRegistration
import com.slack.circuit.serialization.SerializableCircuitSaver
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides

@BindingContainer
interface CircuitSerializationBindings {
    @Multibinds(allowEmpty = true)
    fun registrations(): Set<CircuitSerializerRegistration>
}

@DependencyGraph(
    AppScope::class,
    bindingContainers = [
        CircuitSerializationBindings::class,
        NetworkBindings::class,
        SiteBindings::class,
    ],
)
interface AppGraph {
    val circuit: Circuit
    val circuitSaver: CircuitSaver
    val appSettingsRepository: AppSettingsRepository
    val siteRegistry: SiteRegistry
    val siteRepository: SiteRepository

    @Provides
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit = Circuit.Builder()
        .addUiFactories(uiFactories)
        .addPresenterFactories(presenterFactories)
        .build()

    @Provides
    fun provideCircuitSaver(
        registrations: Set<CircuitSerializerRegistration>,
    ): CircuitSaver = SerializableCircuitSaver(registrations)

    @Suppress("unused")
    @Binds
    val AppSettingsRepositoryImpl.bind: AppSettingsRepository

    @Suppress("unused")
    @Provides
    fun provideDataStore(): DataStore<AppSettings> = createDataStore()
}
