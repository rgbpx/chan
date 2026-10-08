package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class SiteListPresenter(
    @Assisted private val navigator: Navigator,
    private val siteRepository: SiteRepository,
) : Presenter<SiteListScreen.State> {

    @CircuitInject(SiteListScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): SiteListPresenter
    }

    @Composable
    override fun present(): SiteListScreen.State {
        val sites by produceAndCollectAsRetainedState(
            initial = null,
        ) {
            siteRepository.sites
        }

        return when (val sitesMap = sites) {
            null -> SiteListScreen.State.Loading

            else -> SiteListScreen.State.Loaded(
                sitesSettings = sitesMap.values.toList(),
                eventSink = { event ->
                    when (event) {
                        is SiteListScreen.Event.SiteClicked -> {
                            navigator.goTo(
                                EditSiteSettingsScreen(
                                    siteId = event.siteId
                                )
                            )
                        }
                    }
                },
            )
        }
    }
}
