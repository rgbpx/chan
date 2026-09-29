package com.github.rgbpx.chan.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(OnboardingScreen::class, AppScope::class)
@Inject
@Composable
fun OnboardingUi(
    state: OnboardingScreen.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Welcome!")
        Button(onClick = { state.eventSink(OnboardingScreen.Event.Finished) }) {
            Text("Get started")
        }
    }
}

@Preview
@Composable
private fun OnboardingUiPreview() {
    OnboardingUi(
        state = OnboardingScreen.State(
            eventSink = {},
        ),
    )
}
