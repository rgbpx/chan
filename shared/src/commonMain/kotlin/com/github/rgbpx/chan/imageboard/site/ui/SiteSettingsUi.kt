package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.github.rgbpx.chan.imageboard.engine.makaba.MakabaImageboardEngine
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@OptIn(ExperimentalMaterial3Api::class)
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

        is SiteSettingsScreen.State.Loaded -> {
            var expanded by remember {
                mutableStateOf(false)
            }

            Column(
                modifier = modifier.padding(24.dp),
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    },
                ) {
                    OutlinedTextField(
                        value = state.currentEngineId.value,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Engine")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(
                                ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                            )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                    ) {
                        state.engines.forEach { engine ->
                            DropdownMenuItem(
                                text = {
                                    Text(engine.value)
                                },
                                onClick = {
                                    expanded = false
                                    state.eventSink(
                                        SiteSettingsScreen.Event.EngineSelected(engine)
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SiteSettingsUiPreview() {
    SiteSettingsUi(
        state = SiteSettingsScreen.State.Loaded(
            engines = listOf(MakabaImageboardEngine.ID),
            currentEngineId = MakabaImageboardEngine.ID,
            eventSink = {},
        ),
    )
}

