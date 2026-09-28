package com.github.rgbpx.chan.feature.bootstrap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.main.MainScreen
import com.github.rgbpx.chan.feature.onboarding.OnboardingScreen
import com.github.rgbpx.chan.feature.recovery.RecoveryScreen
import com.github.rgbpx.chan.feature.settings.domain.AppSettingsRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.flow.first

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
        var error by remember { mutableStateOf<Throwable?>(null) }

        LaunchedEffect(Unit) {
            try {
                val settings = appSettingsRepository.settings.first()

                when (val corrupted = settings.corruptedBackupFileName) {
                    null -> navigator.resetRoot(
                        when {
                            settings.firstLaunch -> OnboardingScreen
                            else -> MainScreen
                        }
                    )

                    else -> navigator.resetRoot(RecoveryScreen(corrupted))
                }
            } catch (throwable: Throwable) {
                error = throwable
            }
        }

        return when (val bootstrapError = error) {
            null -> BootstrapScreen.State.Loading
            else -> BootstrapScreen.State.Error(bootstrapError)
        }

    }
}
