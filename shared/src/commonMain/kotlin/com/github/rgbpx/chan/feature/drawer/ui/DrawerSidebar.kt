package com.github.rgbpx.chan.feature.drawer.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.symbols.icons.materialsymbols.Icons
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.LeftPanelCloseW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.LeftPanelOpenW400Outlined
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.SettingsW400Outlined

@Composable
fun DrawerSidebar(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(true) }
    val width by animateDpAsState(
        targetValue = when (expanded) {
            true -> 280.dp
            false -> 72.dp
        },
    )

    Row(modifier = modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            modifier = Modifier
                .fillMaxHeight()
                .width(width),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.align(
                        when (expanded) {
                            true -> Alignment.End
                            false -> Alignment.CenterHorizontally
                        },
                    ),
                ) {
                    Icon(
                        imageVector = when (expanded) {
                            true -> Icons.LeftPanelCloseW400Outlined
                            false -> Icons.LeftPanelOpenW400Outlined
                        },
                        contentDescription = when (expanded) {
                            true -> "Collapse sidebar"
                            false -> "Expand sidebar"
                        },
                    )
                }

                // Empty slot for the future history list.
                Box(modifier = Modifier.weight(1f))

                HorizontalDivider()

                DrawerItem(
                    icon = Icons.SettingsW400Outlined,
                    label = "Settings",
                    showLabel = expanded,
                    onClick = onSettingsClick,
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}
