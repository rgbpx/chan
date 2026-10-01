package com.github.rgbpx.chan.feature.bootstrap.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.home.ui.HomeScreen
import com.github.rgbpx.chan.feature.onboarding.ui.OnboardingScreen
import com.github.rgbpx.chan.feature.recovery.ui.RecoveryScreen
import com.github.rgbpx.chan.feature.settings.domain.repository.AppSettingsRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class BootstrapPresenter(
    @Assisted private val navigator: Navigator,
    private val appSettingsRepository: AppSettingsRepository,
) : Presenter<BootstrapScreen.State> {

    @CircuitInject(BootstrapScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): BootstrapPresenter
    }

    @Composable
    override fun present(): BootstrapScreen.State {
        val result by produceAndCollectAsRetainedState(
            initial = null,
        ) {
            appSettingsRepository.settings
        }

        when (val settings = result) {
            null -> Unit
            else ->
                navigator.resetRoot(
                    when (val backupFilename = settings.backupFilename) {
                        null -> when {
                            settings.firstLaunch -> OnboardingScreen
                            else -> HomeScreen
                        }

                        else -> RecoveryScreen(
                            backupFilename = backupFilename,
                        )
                    }
                )
        }

        return BootstrapScreen.State
    }
}
