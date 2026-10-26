package com.github.rgbpx.chan.feature.drawer.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.symbols.icons.materialsymbols.Icons
import com.github.rgbpx.chan.symbols.icons.materialsymbols.icons.SettingsW400Outlined
import kotlinx.coroutines.launch

@Composable
fun DrawerSheet(
    drawerState: DrawerState,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Chan",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp),
                )

                // TODO: Empty slot for the future history list.
                Box(modifier = Modifier.weight(1f))

                HorizontalDivider()

                DrawerItem(
                    icon = Icons.SettingsW400Outlined,
                    label = "Settings",
                    showLabel = true,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onSettingsClick()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                )
            }
        },
        content = content,
    )
}
