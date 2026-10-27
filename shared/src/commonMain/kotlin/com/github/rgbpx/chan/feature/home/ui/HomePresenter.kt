package com.github.rgbpx.chan.feature.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.settings.ui.AppSettingsScreen
import com.github.rgbpx.chan.imageboard.catalog.ui.CatalogScreen
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class HomePresenter(
    @Assisted private val navigator: Navigator,
    private val siteRepository: SiteRepository,
) : Presenter<HomeScreen.State> {

    @CircuitInject(HomeScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): HomePresenter
    }

    @Composable
    override fun present(): HomeScreen.State {
        val sites by produceAndCollectAsRetainedState(initial = null) {
            siteRepository.sites
        }

        val content = when (val sitesMap = sites) {
            null -> HomeScreen.Content.Loading
            else -> when (val site = sitesMap.values.firstOrNull()) {
                null -> HomeScreen.Content.NoSites
                else -> when (val board = site.boards.values.firstOrNull()) {
                    null -> HomeScreen.Content.NoBoards
                    else -> {
                        navigator.resetRoot(CatalogScreen(site.id, board.id))
                        HomeScreen.Content.Loading
                    }
                }
            }
        }

        return HomeScreen.State(
            content = content,
            eventSink = { event ->
                when (event) {
                    HomeScreen.Event.SettingsClicked -> {
                        navigator.goTo(AppSettingsScreen)
                    }
                }
            },
        )
    }
}
