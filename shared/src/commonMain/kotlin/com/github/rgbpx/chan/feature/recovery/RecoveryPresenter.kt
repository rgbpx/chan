package com.github.rgbpx.chan.feature.recovery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.bootstrap.BootstrapScreen
import com.github.rgbpx.chan.feature.settings.domain.AppSettingsRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.launch

@AssistedInject
class RecoveryPresenter(
    @Assisted private val screen: RecoveryScreen,
    @Assisted private val navigator: Navigator,
    private val appSettingsRepository: AppSettingsRepository,
) : Presenter<RecoveryScreen.State> {

    @CircuitInject(RecoveryScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            @Assisted screen: RecoveryScreen,
            @Assisted navigator: Navigator,
        ): RecoveryPresenter
    }

    @Composable
    override fun present(): RecoveryScreen.State {
        val scope = rememberCoroutineScope()
        var copyContent by remember { mutableStateOf<String?>(null) }

        return RecoveryScreen.State(copyContent = copyContent) { event ->
            when (event) {
                RecoveryScreen.Event.CopyClicked -> scope.launch {
                    copyContent =
                        appSettingsRepository.readCorruptedFileContent(screen.corruptedBackupFileName)
                }

                RecoveryScreen.Event.ResetClicked -> scope.launch {
                    appSettingsRepository.resetCorruptedFile()
                    navigator.resetRoot(BootstrapScreen)
                }
            }
        }
    }
}
