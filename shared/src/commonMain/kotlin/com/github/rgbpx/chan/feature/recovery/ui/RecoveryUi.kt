package com.github.rgbpx.chan.feature.recovery.ui

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.platform.clipboard.toClipEntry
import com.github.rgbpx.chan.symbols.icons.materialsymbols.Icons
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.ContentCopyW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.ResetSettingsW400Outlined
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.launch

@CircuitInject(RecoveryScreen::class, AppScope::class)
@Inject
@Composable
fun RecoveryUi(
    state: RecoveryScreen.State,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current
    val isMediumWidth = currentWindowAdaptiveInfoV2()
        .windowSizeClass
        .isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)
    val resetLabel = if (isMediumWidth) "Reset to defaults" else "Reset"
    val copyLabel = if (isMediumWidth) "Copy corrupted file" else "Copy"
    val buttons: @Composable () -> Unit = {
        CopyButton(
            label = copyLabel,
            enabled = state is RecoveryScreen.State.Loaded
        ) {
            when (state) {
                is RecoveryScreen.State.Loaded -> {
                    scope.launch {
                        clipboard.setClipEntry(
                            state.backupContent.toClipEntry()
                        )
                    }
                }

                else -> {}
            }
        }

        ResetButton(
            label = resetLabel,
        ) {
            state.eventSink(RecoveryScreen.Event.Reset)
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
                isMediumWidth -> Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    buttons()
                }

                else -> Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    buttons()
                }
            }
        }
    }
}

@Composable
private fun CopyButton(
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
    ) {
        Icon(
            imageVector = Icons.ContentCopyW400Outlined,
            contentDescription = "Copy corrupted file",
        )

        Spacer(
            Modifier.width(
                ButtonDefaults.IconSpacing
            )
        )

        Text(label)
    }
}

@Composable
private fun ResetButton(
    label: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.ResetSettingsW400Outlined,
            contentDescription = "Reset settings to defaults",
        )

        Spacer(
            Modifier.width(
                ButtonDefaults.IconSpacing
            )
        )

        Text(label)
    }
}

@Preview
@Composable
private fun RecoveryUiPreview() {
    RecoveryUi(
        state = RecoveryScreen.State.Loading {}
    )
}
