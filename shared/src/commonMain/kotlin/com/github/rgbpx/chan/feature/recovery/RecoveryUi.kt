package com.github.rgbpx.chan.feature.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.platform.clipboard.toClipEntry
import com.github.rgbpx.chan.symbols.icons.materialsymbols.Icons
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.ContentCopyW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.ResetSettingsW400Outlined
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(RecoveryScreen::class, AppScope::class)
@Inject
@Composable
fun RecoveryUi(
    state: RecoveryScreen.State,
    modifier: Modifier,
) {
    val clipboard = LocalClipboard.current
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val isMediumWidth = windowSizeClass.isWidthAtLeastBreakpoint(
        WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND,
    )

    LaunchedEffect(state.copyContent) {
        state.copyContent?.let {
            clipboard.setClipEntry(
                it.toClipEntry()
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.widthIn(max = 480.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("App settings were corrupted.")
            Text("A backup of the corrupted settings was created.")

            Spacer(modifier = Modifier.height(24.dp))

            when {
                isMediumWidth -> {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        CopyButton(
                            text = "Copy corrupted file",
                            onClick = { state.eventSink(RecoveryScreen.Event.CopyClicked) },
                        )
                        ResetButton(
                            text = "Reset to defaults",
                            onClick = { state.eventSink(RecoveryScreen.Event.ResetClicked) },
                        )
                    }
                }

                else -> {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        CopyButton(
                            text = "Copy",
                            onClick = { state.eventSink(RecoveryScreen.Event.CopyClicked) },
                        )
                        ResetButton(
                            text = "Reset",
                            onClick = { state.eventSink(RecoveryScreen.Event.ResetClicked) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CopyButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(onClick = onClick) {
        Icon(
            imageVector = Icons.ContentCopyW400Outlined,
            contentDescription = "Copy corrupted file",
        )
        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        Text(text)
    }
}

@Composable
private fun ResetButton(
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(onClick = onClick) {
        Icon(
            imageVector = Icons.ResetSettingsW400Outlined,
            contentDescription = "Reset to defaults",
        )
        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
        Text(text)
    }
}
