package com.github.rgbpx.chan.feature.bootstrap.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.home.ui.HomeScreen
import com.github.rgbpx.chan.feature.onboarding.ui.OnboardingScreen
import com.github.rgbpx.chan.feature.recovery.ui.RecoveryScreen
import com.github.rgbpx.chan.feature.settings.domain.repository.AppSettingsRepository
import com.github.rgbpx.chan.imageboard.catalog.ui.CatalogScreen
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteId
import com.github.rgbpx.chan.imageboard.site.domain.model.SiteSettings
import com.github.rgbpx.chan.imageboard.site.domain.repository.SiteRepository
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.produceAndCollectAsRetainedState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
class BootstrapPresenter(
    @Assisted private val navigator: Navigator,
    private val appSettingsRepository: AppSettingsRepository,
    private val siteRepository: SiteRepository,
) : Presenter<BootstrapScreen.State> {

    @CircuitInject(BootstrapScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): BootstrapPresenter
    }

    @Composable
    override fun present(): BootstrapScreen.State {
        val result by produceAndCollectAsRetainedState(
            initial = null,
        ) {
            appSettingsRepository.settings
        }
        val sites by produceAndCollectAsRetainedState(
            initial = null,
        ) {
            siteRepository.sites
        }

        when (val settings = result) {
            null -> Unit
            else -> when (val backupFilename = settings.backupFilename) {
                null -> when {
                    settings.firstLaunch -> {
                        navigator.resetRoot(OnboardingScreen)
                    }

                    else -> when (val sitesMap = sites) {
                        null -> Unit
                        else -> navigator.resetRoot(
                            startScreen(sitesMap)
                        )
                    }
                }

                else -> {
                    navigator.resetRoot(
                        RecoveryScreen(backupFilename)
                    )
                }
            }
        }

        return BootstrapScreen.State
    }

    private fun startScreen(sites: Map<SiteId, SiteSettings>): Screen {
        return when (val site = sites.values.firstOrNull()) {
            null -> HomeScreen
            else -> when (val board = site.boards.values.firstOrNull()) {
                null -> HomeScreen
                else -> CatalogScreen(site.id, board.id)
            }
        }
    }
}
