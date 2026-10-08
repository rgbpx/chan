package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.EngineRegistry
import com.github.rgbpx.chan.imageboard.site.DEFAULT_ENGINE
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.launch
import kotlin.random.Random

@AssistedInject
class NewSitePresenter(
    @Assisted private val navigator: Navigator,
    private val siteRepository: SiteRepository,
    private val engineRegistry: EngineRegistry,
) : Presenter<NewSiteScreen.State> {

    @CircuitInject(NewSiteScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): NewSitePresenter
    }

    @Composable
    override fun present(): NewSiteScreen.State {
        val siteId = rememberRetained {
            SiteId(Random.nextLong().toString())
        }

        var name by rememberRetained {
            mutableStateOf("")
        }

        var baseUrl by rememberRetained {
            mutableStateOf("")
        }

        var engineId by rememberRetained {
            mutableStateOf(DEFAULT_ENGINE)
        }

        val scope = rememberCoroutineScope()

        return NewSiteScreen.State(
            name = name,
            baseUrl = baseUrl,
            engineId = engineId,
            engineIds = engineRegistry.all().map { it.id },
            eventSink = { event ->
                when (event) {
                    is NewSiteScreen.Event.NameChanged -> {
                        name = event.name
                    }

                    is NewSiteScreen.Event.BaseUrlChanged -> {
                        baseUrl = event.baseUrl
                    }

                    is NewSiteScreen.Event.EngineSelected -> {
                        engineId = event.engineId
                    }

                    NewSiteScreen.Event.SaveClicked -> {
                        scope.launch {
                            siteRepository.save(
                                SiteSettings(
                                    id = siteId,
                                    name = name,
                                    baseUrl = baseUrl,
                                    engineId = engineId,
                                )
                            )

                            navigator.pop()
                        }
                    }
                }
            },
        )
    }
}
