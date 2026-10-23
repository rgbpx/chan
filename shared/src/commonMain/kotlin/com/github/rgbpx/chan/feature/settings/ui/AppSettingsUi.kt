package com.github.rgbpx.chan.feature.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(AppSettingsScreen::class, AppScope::class)
@Inject
@Composable
fun AppSettingsUi(
    state: AppSettingsScreen.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        ListItem(
            headlineContent = {
                Text("Sites")
            },
            modifier = Modifier.clickable {
                state.eventSink(AppSettingsScreen.Event.SitesClicked)
            },
        )
    }
}
