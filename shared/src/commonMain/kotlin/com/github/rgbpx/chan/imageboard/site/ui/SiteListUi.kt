package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.rgbpx.chan.app.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(SiteListScreen::class, AppScope::class)
@Inject
@Composable
fun SiteListUi(
    state: SiteListScreen.State,
    modifier: Modifier = Modifier,
) {
    when (state) {
        SiteListScreen.State.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is SiteListScreen.State.Loaded -> {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            state.eventSink(SiteListScreen.Event.AddClicked)
                        },
                    ) {
                        Text("+")
                    }
                },
            ) { contentPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                ) {
                    items(
                        items = state.sitesSettings,
                        key = { siteSettings -> siteSettings.id.value },
                    ) { siteSettings ->
                        ListItem(
                            headlineContent = {
                                Text(siteSettings.name)
                            },
                            modifier = Modifier.clickable {
                                state.eventSink(
                                    SiteListScreen.Event.SiteClicked(
                                        siteId = siteSettings.id,
                                    )
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}
