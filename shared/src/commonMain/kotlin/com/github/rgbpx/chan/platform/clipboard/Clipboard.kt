package com.github.rgbpx.chan.platform.clipboard

import androidx.compose.ui.platform.ClipEntry

expect fun String.toClipEntry(): ClipEntry
