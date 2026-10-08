package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.makaba.MakabaImageboardEngine
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(SiteSettingsScreen::class, AppScope::class)
@Inject
@Composable
fun SiteSettingsUi(
    state: SiteSettingsScreen.State,
    modifier: Modifier = Modifier,
) {
    when (state) {
        SiteSettingsScreen.State.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        SiteSettingsScreen.State.NotFound -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Site not found")
            }
        }

        is SiteSettingsScreen.State.Loaded -> {
            SiteForm(
                name = state.name,
                baseUrl = state.baseUrl,
                engineId = state.engineId,
                engineIds = state.engineIds,
                onNameChanged = {
                    state.eventSink(
                        SiteSettingsScreen.Event.NameChanged(it)
                    )
                },
                onBaseUrlChanged = {
                    state.eventSink(
                        SiteSettingsScreen.Event.BaseUrlChanged(it)
                    )
                },
                onEngineSelected = {
                    state.eventSink(
                        SiteSettingsScreen.Event.EngineSelected(it)
                    )
                },
                onSaveClicked = {
                    state.eventSink(
                        SiteSettingsScreen.Event.SaveClicked
                    )
                },
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
private fun SiteSettingsUiPreview() {
    SiteSettingsUi(
        state = SiteSettingsScreen.State.Loaded(
            name = "2ch",
            baseUrl = "https://2ch.su",
            engineId = MakabaImageboardEngine.ID,
            engineIds = listOf(MakabaImageboardEngine.ID),
            eventSink = {},
        ),
    )
}

