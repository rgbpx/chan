package com.github.rgbpx.chan.ui

import com.github.rgbpx.chan.feature.settings.domain.AppSettings

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Loaded(val settings: AppSettings) : SettingsUiState
    data class Recovered(val corruptedBackupFileName: String) : SettingsUiState
    data class Error(val throwable: Throwable) : SettingsUiState
}
