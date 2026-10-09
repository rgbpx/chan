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
class EditSitePresenter(
    @Assisted private val screen: EditSiteScreen,
    @Assisted private val navigator: Navigator,
    private val siteRepository: SiteRepository,
    private val engineRegistry: EngineRegistry,
) : Presenter<EditSiteScreen.State> {

    @CircuitInject(EditSiteScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(
            @Assisted screen: EditSiteScreen,
            @Assisted navigator: Navigator,
        ): EditSitePresenter
    }

    @Composable
    override fun present(): EditSiteScreen.State {
        val sites by produceAndCollectAsRetainedState(
            initial = null,
        ) {
            siteRepository.sites
        }

        return when (val sitesMap = sites) {
            null -> EditSiteScreen.State.Loading

            else -> when (val site = sitesMap[screen.siteId]) {
                null -> EditSiteScreen.State.NotFound

                else -> presentLoaded(
                    screen = screen,
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
    screen: EditSiteScreen,
    navigator: Navigator,
    siteRepository: SiteRepository,
    engineRegistry: EngineRegistry,
    site: SiteSettings
): EditSiteScreen.State.Loaded {
    var name by rememberRetained(site) { mutableStateOf(site.name) }
    var baseUrl by rememberRetained(site) { mutableStateOf(site.baseUrl) }
    var engineId by rememberRetained(site) { mutableStateOf(site.engineId) }
    val scope = rememberCoroutineScope()

    val baseUrlError = validateBaseUrl(baseUrl)
    val canSave = name.isNotBlank() &&
            baseUrl.isNotBlank() &&
            baseUrlError == null

    return EditSiteScreen.State.Loaded(
        name = name,
        baseUrl = baseUrl,
        engineId = engineId,
        engineIds = engineRegistry.all().map { it.id },
        baseUrlError = baseUrlError,
        canSave = canSave,
        eventSink = { event ->
            when (event) {
                is EditSiteScreen.Event.NameChanged -> {
                    name = event.name
                }

                is EditSiteScreen.Event.BaseUrlChanged -> {
                    baseUrl = event.baseUrl
                }

                is EditSiteScreen.Event.EngineSelected -> {
                    engineId = event.engineId
                }

                EditSiteScreen.Event.SaveClicked -> {
                    if (canSave) {
                        scope.launch {
                            siteRepository.save(
                                site.copy(
                                    name = name.trim(),
                                    baseUrl = baseUrl.trim(),
                                    engineId = engineId,
                                ),
                            )

                            navigator.pop()
                        }
                    }
                }

                EditSiteScreen.Event.DeleteClicked -> {
                    scope.launch {
                        siteRepository.remove(screen.siteId)
                        navigator.pop()
                    }
                }

                EditSiteScreen.Event.CancelClicked -> {
                    navigator.pop()
                }
            }
        },
    )
}
