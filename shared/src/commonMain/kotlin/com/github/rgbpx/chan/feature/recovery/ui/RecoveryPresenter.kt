package com.github.rgbpx.chan.feature.recovery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.bootstrap.ui.BootstrapScreen
import com.github.rgbpx.chan.feature.settings.domain.repository.AppSettingsRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
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
        val result by produceAndCollectAsRetainedState(
            screen.backupFilename,
            initial = null,
        ) {
            appSettingsRepository.readCorruptedFileContent(
                screen.backupFilename
            )
        }

        val eventSink: (RecoveryScreen.Event) -> Unit = { event ->
            when (event) {
                RecoveryScreen.Event.Reset -> scope.launch {
                    appSettingsRepository.resetCorruptedFile()
                    navigator.resetRoot(BootstrapScreen)
                }
            }
        }

        return when (val content = result) {
            null -> RecoveryScreen.State.Loading(
                eventSink = eventSink,
            )

            else -> RecoveryScreen.State.Loaded(
                backupContent = content,
                eventSink = eventSink,
            )
        }
    }
}
