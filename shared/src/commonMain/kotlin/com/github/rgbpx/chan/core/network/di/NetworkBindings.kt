package com.github.rgbpx.chan.core.network.di

import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.core.network.createHttpClient
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient

@BindingContainer
object NetworkBindings {
    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClient(): HttpClient = createHttpClient()
}
