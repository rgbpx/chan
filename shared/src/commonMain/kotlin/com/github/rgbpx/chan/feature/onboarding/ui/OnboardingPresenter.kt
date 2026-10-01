package com.github.rgbpx.chan.feature.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.bootstrap.ui.BootstrapScreen
import com.github.rgbpx.chan.feature.settings.domain.repository.AppSettingsRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.launch

@AssistedInject
class OnboardingPresenter(
    @Assisted private val navigator: Navigator,
    private val appSettingsRepository: AppSettingsRepository,
) : Presenter<OnboardingScreen.State> {

    @CircuitInject(OnboardingScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): OnboardingPresenter
    }

    @Composable
    override fun present(): OnboardingScreen.State {
        val scope = rememberCoroutineScope()

        return OnboardingScreen.State { event ->
            when (event) {
                OnboardingScreen.Event.Finished -> {
                    scope.launch {
                        appSettingsRepository.setFirstLaunchCompleted()

                        navigator.resetRoot(BootstrapScreen)
                    }
                }
            }
        }
    }
}
