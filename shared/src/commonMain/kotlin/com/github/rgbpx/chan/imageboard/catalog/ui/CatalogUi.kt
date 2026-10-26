package com.github.rgbpx.chan.imageboard.catalog.ui

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
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.app.di.AppScope
import com.github.rgbpx.chan.imageboard.thread.domain.model.Thread
import com.github.rgbpx.chan.imageboard.thread.domain.model.ThreadId
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.Inject

@CircuitInject(CatalogScreen::class, AppScope::class)
@Inject
@Composable
fun CatalogUi(
    state: CatalogScreen.State,
    modifier: Modifier = Modifier,
) {
    val content = state.content

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = when (content) {
                            is CatalogScreen.Content.Loaded -> content.boardName
                            else -> "Catalog"
                        },
                    )
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
                CatalogScreen.Content.Loading -> {
                    CircularProgressIndicator()
                }

                CatalogScreen.Content.SiteNotFound -> {
                    Text("Site not found")
                }

                CatalogScreen.Content.UnknownEngine -> {
                    Text("Unknown engine")
                }

                CatalogScreen.Content.BoardNotFound -> {
                    Text("Board not found")
                }

                is CatalogScreen.Content.Loaded -> {
                    when (val threads = content.threads) {
                        CatalogScreen.Threads.Loading -> {
                            CircularProgressIndicator()
                        }

                        is CatalogScreen.Threads.Error -> {
                            Text(
                                text = threads.message,
                                modifier = Modifier.padding(24.dp),
                            )
                        }

                        is CatalogScreen.Threads.Loaded -> {
                            when {
                                threads.threads.isEmpty() -> {
                                    Text("No threads")
                                }

                                else -> {
                                    ThreadList(threads = threads.threads)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ThreadList(
    threads: List<Thread>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
    ) {
        itemsIndexed(
            items = threads,
            key = { _, thread -> thread.id.value },
        ) { index, thread ->
            ThreadListItem(
                thread = thread,
                index = index,
                count = threads.size,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ThreadListItem(
    thread: Thread,
    index: Int,
    count: Int,
    modifier: Modifier = Modifier,
) {
    SegmentedListItem(
        onClick = {},
        shapes = ListItemDefaults.segmentedShapes(index = index, count = count),
        modifier = modifier,
        content = {
            Text(
                text = thread.title(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        },
    )
}

private fun Thread.title(): String {
    return when {
        subject.isNotBlank() -> subject
        comment.isNotBlank() -> comment
        else -> "No subject"
    }
}

@Preview
@Composable
private fun CatalogUiPreview() {
    CatalogUi(
        state = CatalogScreen.State(
            content = CatalogScreen.Content.Loaded(
                boardName = "Бред",
                threads = CatalogScreen.Threads.Loaded(
                    listOf(
                        Thread(
                            id = ThreadId(1L),
                            subject = "Thread subject",
                            comment = "",
                            postsCount = 10,
                        ),
                        Thread(
                            id = ThreadId(2L),
                            subject = "",
                            comment = "Comment used as the title",
                            postsCount = 3,
                        ),
                    ),
                ),
            ),
        ),
    )
}
