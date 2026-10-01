package com.github.rgbpx.chan.feature.home.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject

@CircuitInject(HomeScreen::class, AppScope::class)
@Composable
fun HomeUi(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Main screen")
    }
}

@Preview
@Composable
private fun HomeUiPreview() {
    HomeUi()
}
