package com.github.rgbpx.chan.feature.home.ui

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
class HomePresenter(
    @Assisted private val navigator: Navigator,
) : Presenter<HomeScreen.State> {

    @CircuitInject(HomeScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): HomePresenter
    }

    @Composable
    override fun present(): HomeScreen.State {
        return HomeScreen.State { event ->
            when (event) {
                HomeScreen.Event.SitesClicked -> {
                    navigator.goTo(SiteListScreen)
                }
            }
        }
    }
}
