package com.github.rgbpx.chan.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.feature.drawer.ui.AdaptiveDrawer
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(HomeScreen::class, AppScope::class)
@Inject
@Composable
fun HomeUi(
    state: HomeScreen.State,
    modifier: Modifier = Modifier,
) {
    AdaptiveDrawer(
        onSettingsClick = {
            state.eventSink(HomeScreen.Event.SettingsClicked)
        },
        modifier = modifier,
    ) { menuButton ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Home")
                    },
                    navigationIcon = menuButton,
                )
            },
        ) { contentPadding ->
            // Placeholder until the catalog replaces Home.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
            )
        }
    }
}
