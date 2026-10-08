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

@CircuitInject(EditSiteSettingsScreen::class, AppScope::class)
@Inject
@Composable
fun EditSiteSettingsUi(
    state: EditSiteSettingsScreen.State,
    modifier: Modifier = Modifier,
) {
    when (state) {
        EditSiteSettingsScreen.State.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        EditSiteSettingsScreen.State.NotFound -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Site not found")
            }
        }

        is EditSiteSettingsScreen.State.Loaded -> {
            SiteForm(
                name = state.name,
                baseUrl = state.baseUrl,
                engineId = state.engineId,
                engineIds = state.engineIds,
                onNameChanged = {
                    state.eventSink(
                        EditSiteSettingsScreen.Event.NameChanged(it)
                    )
                },
                onBaseUrlChanged = {
                    state.eventSink(
                        EditSiteSettingsScreen.Event.BaseUrlChanged(it)
                    )
                },
                onEngineSelected = {
                    state.eventSink(
                        EditSiteSettingsScreen.Event.EngineSelected(it)
                    )
                },
                onSaveClicked = {
                    state.eventSink(
                        EditSiteSettingsScreen.Event.SaveClicked
                    )
                },
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
private fun EditSiteSettingsUiPreview() {
    EditSiteSettingsUi(
        state = EditSiteSettingsScreen.State.Loaded(
            name = "2ch",
            baseUrl = "https://2ch.su",
            engineId = MakabaImageboardEngine.ID,
            engineIds = listOf(MakabaImageboardEngine.ID),
            eventSink = {},
        ),
    )
}

