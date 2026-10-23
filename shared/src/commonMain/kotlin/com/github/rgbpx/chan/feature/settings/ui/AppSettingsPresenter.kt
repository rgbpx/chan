package com.github.rgbpx.chan.feature.settings.ui

import androidx.compose.runtime.Composable
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.site.ui.SiteListScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class AppSettingsPresenter(
    @Assisted private val navigator: Navigator,
) : Presenter<AppSettingsScreen.State> {

    @CircuitInject(AppSettingsScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): AppSettingsPresenter
    }

    @Composable
    override fun present(): AppSettingsScreen.State {
        return AppSettingsScreen.State { event ->
            when (event) {
                AppSettingsScreen.Event.SitesClicked -> {
                    navigator.goTo(SiteListScreen)
                }
            }
        }
    }
}
