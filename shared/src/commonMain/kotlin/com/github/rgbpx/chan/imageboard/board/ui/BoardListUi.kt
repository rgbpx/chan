package com.github.rgbpx.chan.imageboard.board.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import com.github.rgbpx.chan.symbols.icons.materialsymbols.Icons
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.ArrowBackW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.RefreshW400Outlined
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(BoardListScreen::class, AppScope::class)
@Inject
@Composable
fun BoardListUi(
    state: BoardListScreen.State,
    modifier: Modifier = Modifier,
) {
    val content = state.content

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BoardListTopBar(
                refreshEnabled = content is BoardListScreen.Content.Loaded ||
                        content is BoardListScreen.Content.Error,
                onBackClicked = {
                    state.eventSink(BoardListScreen.Event.BackClicked)
                },
                onRefreshClicked = {
                    state.eventSink(BoardListScreen.Event.RefreshClicked)
                },
            )
        },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            when (content) {
                BoardListScreen.Content.Loading -> {
                    CircularProgressIndicator()
                }

                BoardListScreen.Content.SiteNotFound -> {
                    Text("Site not found")
                }

                BoardListScreen.Content.UnknownEngine -> {
                    Text("Unknown engine")
                }

                is BoardListScreen.Content.Error -> {
                    Text(
                        text = content.message,
                        modifier = Modifier.padding(24.dp),
                    )
                }

                is BoardListScreen.Content.Loaded -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            items = content.boards,
                            key = { board -> board.id.value },
                        ) { board ->
                            ListItem(
                                headlineContent = {
                                    Text(board.name)
                                },
                                supportingContent = {
                                    Text(board.displayId)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardListTopBar(
    refreshEnabled: Boolean,
    onBackClicked: () -> Unit,
    onRefreshClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text("Boards")
        },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.ArrowBackW400Outlined,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            IconButton(
                onClick = onRefreshClicked,
                enabled = refreshEnabled,
            ) {
                Icon(
                    imageVector = Icons.RefreshW400Outlined,
                    contentDescription = "Refresh",
                )
            }
        },
    )
}

@Preview
@Composable
private fun BoardListUiPreview() {
    BoardListUi(
        state = BoardListScreen.State(
            content = BoardListScreen.Content.Loaded(
                boards = listOf(
                    Board(id = BoardId("b"), name = "Бред"),
                    Board(id = BoardId("a"), name = "Аниме"),
                ),
            ),
            eventSink = {},
        ),
    )
}
