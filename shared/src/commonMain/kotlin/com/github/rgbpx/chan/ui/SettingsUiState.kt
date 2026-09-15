package com.github.rgbpx.chan.ui

import com.github.rgbpx.chan.settings.AppSettings

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Loaded(val settings: AppSettings) : SettingsUiState
}
