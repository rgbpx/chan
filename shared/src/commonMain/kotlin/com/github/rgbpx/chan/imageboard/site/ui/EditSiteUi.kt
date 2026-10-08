package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.engine.makaba.MakabaImageboardEngine
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(EditSiteScreen::class, AppScope::class)
@Inject
@Composable
fun EditSiteUi(
    state: EditSiteScreen.State,
    modifier: Modifier = Modifier,
) {
    when (state) {
        EditSiteScreen.State.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        EditSiteScreen.State.NotFound -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Site not found")
            }
        }

        is EditSiteScreen.State.Loaded -> {
            Column(
                modifier = modifier,
            ) {
                SiteForm(
                    name = state.name,
                    baseUrl = state.baseUrl,
                    engineId = state.engineId,
                    engineIds = state.engineIds,
                    onNameChanged = {
                        state.eventSink(
                            EditSiteScreen.Event.NameChanged(it)
                        )
                    },
                    onBaseUrlChanged = {
                        state.eventSink(
                            EditSiteScreen.Event.BaseUrlChanged(it)
                        )
                    },
                    onEngineSelected = {
                        state.eventSink(
                            EditSiteScreen.Event.EngineSelected(it)
                        )
                    },
                    onSaveClicked = {
                        state.eventSink(
                            EditSiteScreen.Event.SaveClicked
                        )
                    },
                    modifier = modifier,
                )

                Button(
                    onClick = {
                        state.eventSink(
                            EditSiteScreen.Event.DeleteClicked
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditSiteUiPreview() {
    EditSiteUi(
        state = EditSiteScreen.State.Loaded(
            name = "2ch",
            baseUrl = "https://2ch.su",
            engineId = MakabaImageboardEngine.ID,
            engineIds = listOf(MakabaImageboardEngine.ID),
            eventSink = {},
        ),
    )
}

