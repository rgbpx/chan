package com.github.rgbpx.chan.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.feature.settings.domain.AppSettings
import com.github.rgbpx.chan.feature.settings.domain.AppSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private val log = Logger.withTag("AppShellViewModel")

class AppShellViewModel(
    private val appSettingsRepository: AppSettingsRepository,
) : ViewModel() {

    val uiState: StateFlow<AppUiState> = appSettingsRepository.settings
        .map<AppSettings, AppUiState> {
            when (val corruptedFileName = it.corruptedBackupFileName) {
                null -> AppUiState.Loaded(it)
                else -> AppUiState.Recovered(corruptedFileName)
            }
        }
        .catch {
            log.e(it) { "Failed to load app settings" }
            emit(AppUiState.Error(it))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppUiState.Loading,
        )

    fun onOnboardingFinished() {
        viewModelScope.launch {
            appSettingsRepository.setFirstLaunchCompleted()
        }
    }

    fun onResetCorruptedFile() {
        viewModelScope.launch {
            appSettingsRepository.resetCorruptedFile()
        }
    }

    suspend fun readCorruptedFileContent(fileName: String): String =
        appSettingsRepository.readCorruptedFileContent(fileName)
}
