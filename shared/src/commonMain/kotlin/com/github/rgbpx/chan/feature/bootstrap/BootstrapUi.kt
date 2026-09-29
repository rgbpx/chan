package com.github.rgbpx.chan.feature.bootstrap

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.core.ui.ErrorScreen
import com.github.rgbpx.chan.core.ui.LoadingScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(BootstrapScreen::class, AppScope::class)
@Inject
@Composable
fun BootstrapUi(
    state: BootstrapScreen.State,
    modifier: Modifier = Modifier,
) {
    when (state) {
        BootstrapScreen.State.Loading -> LoadingScreen()
        is BootstrapScreen.State.Error -> ErrorScreen(throwable = state.throwable)
    }
}

@Preview
@Composable
private fun BootstrapErrorUiPreview() {
    BootstrapUi(
        state = BootstrapScreen.State.Error(
            throwable = Exception("Preview error"),
        ),
    )
}
