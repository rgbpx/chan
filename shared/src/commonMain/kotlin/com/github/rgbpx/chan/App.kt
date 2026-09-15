package com.github.rgbpx.chan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map

import chan.shared.generated.resources.Res
import chan.shared.generated.resources.compose_multiplatform
import com.github.rgbpx.chan.settings.AppSettings
import com.github.rgbpx.chan.settings.AppSettingsRepository
import com.github.rgbpx.chan.ui.LoadingScreen
import com.github.rgbpx.chan.ui.MainScreen
import com.github.rgbpx.chan.ui.OnboardingScreen
import com.github.rgbpx.chan.ui.SettingsUiState

@Composable
fun App(appSettingsRepository: AppSettingsRepository) {
    val uiState by remember(appSettingsRepository) {
        appSettingsRepository.settings.map<AppSettings, SettingsUiState> {
            SettingsUiState.Loaded(it)
        }
    }.collectAsState(initial = SettingsUiState.Loading)
    val scope = rememberCoroutineScope()

    when (val state = uiState) {
        is SettingsUiState.Loading -> LoadingScreen()
        is SettingsUiState.Loaded -> if (state.settings.firstLaunch) {
            OnboardingScreen(onFinished = {
                scope.launch { appSettingsRepository.setFirstLaunchCompleted() }
            })
        } else {
            MainScreen()
        }
    }
}
