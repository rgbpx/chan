package com.github.rgbpx.chan.feature.drawer.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    showLabel: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (showLabel) {
        true -> NavigationDrawerItem(
            label = {
                Text(label)
            },
            selected = false,
            onClick = onClick,
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = label
                )
            },
            modifier = modifier,
        )

        false -> IconButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label
            )
        }
    }
}
