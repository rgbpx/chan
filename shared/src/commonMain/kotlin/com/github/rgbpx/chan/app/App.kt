package com.github.rgbpx.chan.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalClipboard
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.rgbpx.chan.app.di.AppGraph
import com.github.rgbpx.chan.core.ui.ErrorScreen
import com.github.rgbpx.chan.core.ui.LoadingScreen
import com.github.rgbpx.chan.platform.clipboard.toClipEntry
import com.github.rgbpx.chan.ui.MainScreen
import com.github.rgbpx.chan.ui.OnboardingScreen
import com.github.rgbpx.chan.ui.RecoverScreen
import dev.zacsweers.metro.createGraph
import kotlinx.coroutines.launch

@Composable
fun App(
    appGraph: AppGraph = remember {
        createGraph()
    }
) {
    val viewModel = viewModel<AppShellViewModel>(
        factory = viewModelFactory {
            initializer {
                AppShellViewModel(appGraph.appSettingsRepository)
            }
        }
    )

    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current

    when (val state = uiState) {
        is AppUiState.Loading -> LoadingScreen()

        is AppUiState.Error -> ErrorScreen(throwable = state.throwable)

        is AppUiState.Recovered -> RecoverScreen(
            onCopyClick = {
                scope.launch {
                    val content = viewModel.readCorruptedFileContent(state.corruptedBackupFileName)

                    clipboard.setClipEntry(content.toClipEntry())
                }
            },
            onResetClick = viewModel::onResetCorruptedFile,
        )

        is AppUiState.Loaded -> when {
            state.settings.firstLaunch ->
                OnboardingScreen(onFinished = viewModel::onOnboardingFinished)

            else -> MainScreen()
        }
    }
}
