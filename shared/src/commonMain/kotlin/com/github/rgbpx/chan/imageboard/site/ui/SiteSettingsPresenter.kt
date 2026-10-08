package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineRegistry
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class SiteSettingsPresenter(
    @Assisted private val screen: SiteSettingsScreen,
    @Assisted private val navigator: Navigator,
    private val siteRepository: SiteRepository,
    private val engineRegistry: EngineRegistry,
) : Presenter<SiteSettingsScreen.State> {

    @CircuitInject(SiteSettingsScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            @Assisted screen: SiteSettingsScreen,
            @Assisted navigator: Navigator,
        ): SiteSettingsPresenter
    }

    @Composable
    override fun present(): SiteSettingsScreen.State {
        val sites by produceAndCollectAsRetainedState(
            initial = null,
        ) {
            siteRepository.sites
        }

        return when (val sitesMap = sites) {
            null -> SiteSettingsScreen.State.Loading

            else -> when (val site = sitesMap[screen.siteId]) {
                null -> SiteSettingsScreen.State.NotFound

                else -> SiteSettingsScreen.State.Loaded(
                    site = site,
                    engineIds = engineRegistry.all().map { it.id },
                    eventSink = { event ->
                        when (event) {
                            is SiteSettingsScreen.Event.EngineSelected -> {
                            }
                        }
                    },
                )
            }
        }
    }
}
