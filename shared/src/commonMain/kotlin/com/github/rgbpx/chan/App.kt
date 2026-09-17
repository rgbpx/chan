package com.github.rgbpx.chan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.settings.AppSettings
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.ui.ErrorScreen
import com.github.rgbpx.chan.ui.LoadingScreen
import com.github.rgbpx.chan.ui.MainScreen
import com.github.rgbpx.chan.ui.OnboardingScreen
import com.github.rgbpx.chan.ui.SettingsUiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val log = Logger.withTag("App")

@Composable
fun App(appSettingsRepository: AppSettingsRepository) {
    val uiState by remember(appSettingsRepository) {
        appSettingsRepository.settings
            .map<AppSettings, SettingsUiState> {
                SettingsUiState.Loaded(it)
            }
            .catch {
                log.e(it) { "Failed to load app settings" }
                emit(SettingsUiState.Error(it))
            }
    }.collectAsState(initial = SettingsUiState.Loading)

    val scope = rememberCoroutineScope()

    when (val state = uiState) {
        is SettingsUiState.Loading -> LoadingScreen()
        is SettingsUiState.Error -> ErrorScreen(state.throwable)
        is SettingsUiState.Loaded -> if (state.settings.firstLaunch) {
            OnboardingScreen(onFinished = {
                scope.launch { appSettingsRepository.setFirstLaunchCompleted() }
            })
        } else {
            MainScreen()
        }
    }
}
