package com.github.rgbpx.chan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val WideLayoutBreakpoint = 600.dp

@Composable
fun ErrorScreen(
    throwable: Throwable,
    onCopyClick: () -> Unit,
    onResetClick: () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        val isWide = maxWidth >= WideLayoutBreakpoint

        Column(
            modifier = Modifier.widthIn(max = 480.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("App settings failed to load.")
            Text(throwable.message ?: "Unknown error")

            Spacer(modifier = Modifier.height(24.dp))

            when {
                isWide -> {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = onCopyClick) { Text("Copy corrupted file") }
                        OutlinedButton(onClick = onResetClick) { Text("Reset to defaults") }
                    }
                }
                else -> {
                    Button(onClick = onCopyClick) { Text("Copy corrupted file") }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = onResetClick) { Text("Reset to defaults") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(
        throwable = RuntimeException("Preview error"),
        onCopyClick = {},
        onResetClick = {},
    )
}
