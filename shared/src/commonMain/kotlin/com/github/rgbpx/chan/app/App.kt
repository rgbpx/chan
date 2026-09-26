package com.github.rgbpx.chan.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalClipboard
import co.touchlab.kermit.Logger
import com.github.rgbpx.chan.app.di.AppGraph
import com.github.rgbpx.chan.core.ui.ErrorScreen
import com.github.rgbpx.chan.core.ui.LoadingScreen
import com.github.rgbpx.chan.feature.settings.domain.AppSettings
import com.github.rgbpx.chan.platform.clipboard.toClipEntry
import com.github.rgbpx.chan.ui.MainScreen
import com.github.rgbpx.chan.ui.OnboardingScreen
import com.github.rgbpx.chan.ui.RecoverScreen
import com.github.rgbpx.chan.ui.SettingsUiState
import dev.zacsweers.metro.createGraph
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val log = Logger.withTag("App")

@Composable
fun App(appGraph: AppGraph = remember { createGraph() }) {
    val uiState by remember(appGraph.appSettingsRepository) {
        appGraph.appSettingsRepository.settings
            .map<AppSettings, SettingsUiState> {
                when (val corruptedFileName = it.corruptedBackupFileName) {
                    null -> SettingsUiState.Loaded(it)
                    else -> SettingsUiState.Recovered(corruptedFileName)
                }
            }
            .catch {
                log.e(it) { "Failed to load app settings" }
                emit(SettingsUiState.Error(it))
            }
    }.collectAsState(initial = SettingsUiState.Loading)

    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current

    when (val state = uiState) {
        is SettingsUiState.Loading -> LoadingScreen()

        is SettingsUiState.Error -> ErrorScreen(
            throwable = state.throwable,
        )

        is SettingsUiState.Recovered -> RecoverScreen(
            onCopyClick = {
                scope.launch {
                    val content = appGraph.appSettingsRepository.readCorruptedFileContent(
                        state.corruptedBackupFileName,
                    )

                    clipboard.setClipEntry(content.toClipEntry())
                }
            },
            onResetClick = {
                scope.launch {
                    appGraph.appSettingsRepository.resetCorruptedFile()
                }
            },
        )

        is SettingsUiState.Loaded -> when {
            state.settings.firstLaunch -> {
                OnboardingScreen(
                    onFinished = {
                        scope.launch {
                            appGraph.appSettingsRepository.setFirstLaunchCompleted()
                        }
                    },
                )
            }

            else -> MainScreen()
        }
    }
}
