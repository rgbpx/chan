package com.github.rgbpx.chan.app

import com.github.rgbpx.chan.feature.settings.domain.AppSettings

sealed interface AppUiState {
    data object Loading : AppUiState
    data class Loaded(val settings: AppSettings) : AppUiState
    data class Recovered(val corruptedBackupFileName: String) : AppUiState
    data class Error(val throwable: Throwable) : AppUiState
}
