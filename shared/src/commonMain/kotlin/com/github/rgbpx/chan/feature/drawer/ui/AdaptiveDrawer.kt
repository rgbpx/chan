package com.github.rgbpx.chan.feature.drawer.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import com.github.rgbpx.chan.symbols.icons.materialsymbols.Icons
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.MenuW400Outlined
import kotlinx.coroutines.launch

private val NoMenuButton: @Composable () -> Unit = {}

@Composable
fun AdaptiveDrawer(
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (menuButton: @Composable () -> Unit) -> Unit,
) {
    val isExpandedWidth = currentWindowAdaptiveInfoV2()
        .windowSizeClass
        .isWidthAtLeastBreakpoint(
            widthDpBreakpoint = WIDTH_DP_EXPANDED_LOWER_BOUND
        )

    when (isExpandedWidth) {
        true -> DrawerSidebar(
            onSettingsClick = onSettingsClick,
            modifier = modifier,
        ) {
            content(NoMenuButton)
        }

        false -> {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            DrawerSheet(
                drawerState = drawerState,
                onSettingsClick = onSettingsClick,
                modifier = modifier,
            ) {
                content {
                    DrawerMenuButton(
                        onClick = {
                            scope.launch { drawerState.open() }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerMenuButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.MenuW400Outlined,
            contentDescription = "Open menu",
        )
    }
}
