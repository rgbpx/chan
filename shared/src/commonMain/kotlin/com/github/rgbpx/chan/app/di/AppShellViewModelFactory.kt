package com.github.rgbpx.chan.app.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.rgbpx.chan.app.AppShellViewModel

internal fun AppGraph.appShellViewModelFactory() = viewModelFactory {
    initializer {
        AppShellViewModel(appSettingsRepository)
    }
}
