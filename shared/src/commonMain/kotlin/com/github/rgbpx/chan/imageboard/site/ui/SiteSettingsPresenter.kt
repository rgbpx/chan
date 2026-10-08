package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineRegistry
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.launch

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

                else -> presentLoaded(
                    navigator = navigator,
                    siteRepository = siteRepository,
                    engineRegistry = engineRegistry,
                    site = site,
                )
            }
        }
    }
}

@Composable
private fun presentLoaded(
    navigator: Navigator,
    siteRepository: SiteRepository,
    engineRegistry: EngineRegistry,
    site: SiteSettings
): SiteSettingsScreen.State.Loaded {
    var name by rememberRetained(site) { mutableStateOf(site.name) }
    var baseUrl by rememberRetained(site) { mutableStateOf(site.baseUrl) }
    var engineId by rememberRetained(site) { mutableStateOf(site.engineId) }
    val scope = rememberCoroutineScope()

    return SiteSettingsScreen.State.Loaded(
        name = name,
        baseUrl = baseUrl,
        engineId = engineId,
        engineIds = engineRegistry.all().map { it.id },
        eventSink = { event ->
            when (event) {
                is SiteSettingsScreen.Event.NameChanged -> {
                    name = event.name
                }

                is SiteSettingsScreen.Event.BaseUrlChanged -> {
                    baseUrl = event.baseUrl
                }

                is SiteSettingsScreen.Event.EngineSelected -> {
                    engineId = event.engineId
                }

                SiteSettingsScreen.Event.SaveClicked -> {
                    scope.launch {
                        siteRepository.save(
                            site.copy(
                                name = name,
                                baseUrl = baseUrl,
                                engineId = engineId,
                            ),
                        )

                        navigator.pop()
                    }
                }
            }
        },
    )
}
