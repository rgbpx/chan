package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.site.DEFAULT_ENGINE
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
            var showDeleteConfirmation by remember {
                mutableStateOf(false)
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.widthIn(max = 480.dp),
                ) {
                    ListItem(
                        headlineContent = {
                            Text("Boards")
                        },
                        supportingContent = {
                            Text("Setup boards")
                        },
                        modifier = Modifier.clickable {
                            state.eventSink(EditSiteScreen.Event.BoardsClicked)
                        },
                    )

                    SiteForm(
                        name = state.name,
                        baseUrl = state.baseUrl,
                        engineId = state.engineId,
                        engineIds = state.engineIds,
                        baseUrlError = state.baseUrlError,
                        canSave = state.canSave,
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
                        onCancelClicked = {
                            state.eventSink(
                                EditSiteScreen.Event.CancelClicked
                            )
                        },
                        modifier = Modifier,
                    )

                    OutlinedButton(
                        onClick = {
                            showDeleteConfirmation = true
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                    ) {
                        Text("Delete")
                    }
                }
            }

            if (showDeleteConfirmation) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteConfirmation = false
                    },
                    title = {
                        Text("Delete site?")
                    },
                    text = {
                        Text("This site will be permanently removed.")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDeleteConfirmation = false
                                state.eventSink(
                                    EditSiteScreen.Event.DeleteClicked
                                )
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error,
                            ),
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteConfirmation = false
                            },
                        ) {
                            Text("Cancel")
                        }
                    },
                )
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
            engineId = DEFAULT_ENGINE,
            engineIds = listOf(DEFAULT_ENGINE),
            baseUrlError = null,
            canSave = true,
            eventSink = {},
        ),
    )
}

