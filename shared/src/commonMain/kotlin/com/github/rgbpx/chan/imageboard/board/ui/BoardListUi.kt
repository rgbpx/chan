package com.github.rgbpx.chan.imageboard.board.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.board.domain.model.Board
import com.github.rgbpx.chan.imageboard.board.domain.model.BoardId
import com.github.rgbpx.chan.symbols.icons.materialsymbols.Icons
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.AddW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.ArrowBackW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.CloseW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.RefreshW400Outlined
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
                    ) {
                        if (content.added.isNotEmpty()) {
                            item(key = "header:added") {
                                Text(
                                    text = "Added boards",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                )
                            }

                            itemsIndexed(
                                items = content.added,
                                key = { _, board -> board.id.value },
                            ) { index, board ->
                                BoardListItem(
                                    board = board,
                                    index = index,
                                    count = content.added.size,
                                    dimmed = false,
                                    actionIcon = Icons.CloseW400Outlined,
                                    actionDescription = "Remove board",
                                    onActionClicked = {
                                        state.eventSink(
                                            BoardListScreen.Event.RemoveBoardClicked(board.id)
                                        )
                                    },
                                )
                            }
                        }

                        if (content.available.isNotEmpty()) {
                            if (content.added.isNotEmpty()) {
                                item(key = "separator") {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 12.dp),
                                    )
                                }
                            }

                            item(key = "header:available") {
                                Text(
                                    text = "Available boards",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    ),
                                )
                            }

                            itemsIndexed(
                                items = content.available,
                                key = { _, board -> board.id.value },
                            ) { index, board ->
                                BoardListItem(
                                    board = board,
                                    index = index,
                                    count = content.available.size,
                                    dimmed = true,
                                    actionIcon = Icons.AddW400Outlined,
                                    actionDescription = "Add board",
                                    onActionClicked = {
                                        state.eventSink(
                                            BoardListScreen.Event.AddBoardClicked(board)
                                        )
                                    },
                                )
                            }
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun BoardListItem(
    board: Board,
    index: Int,
    count: Int,
    dimmed: Boolean,
    actionIcon: ImageVector,
    actionDescription: String,
    onActionClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = when {
        dimmed -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        else -> Color.Unspecified
    }

    SegmentedListItem(
        onClick = onActionClicked,
        shapes = ListItemDefaults.segmentedShapes(index = index, count = count),
        modifier = modifier,
        supportingContent = {
            Text(text = board.displayId, color = textColor)
        },
        trailingContent = {
            IconButton(onClick = onActionClicked) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionDescription,
                )
            }
        },
        content = {
            Text(text = board.name, color = textColor)
        },
    )
}

@Preview
@Composable
private fun BoardListUiPreview() {
    BoardListUi(
        state = BoardListScreen.State(
            content = BoardListScreen.Content.Loaded(
                added = listOf(
                    Board(id = BoardId("b"), name = "Бред"),
                ),
                available = listOf(
                    Board(id = BoardId("a"), name = "Аниме"),
                ),
            ),
            eventSink = {},
        ),
    )
}
