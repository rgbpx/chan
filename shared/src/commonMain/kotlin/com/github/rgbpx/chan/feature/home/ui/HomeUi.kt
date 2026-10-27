package com.github.rgbpx.chan.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentAlignment = Alignment.Center,
            ) {
                when (state.content) {
                    HomeScreen.Content.Loading -> {
                        CircularProgressIndicator()
                    }

                    HomeScreen.Content.NoSites -> {
                        Text(
                            text = "No sites added",
                            modifier = Modifier.padding(24.dp),
                        )
                    }

                    HomeScreen.Content.NoBoards -> {
                        Text(
                            text = "No boards added. Add one in Settings",
                            modifier = Modifier.padding(24.dp),
                        )
                    }
                }
            }
        }
    }
}
