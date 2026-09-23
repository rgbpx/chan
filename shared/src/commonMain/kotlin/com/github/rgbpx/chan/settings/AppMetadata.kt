package com.github.rgbpx.chan.settings

interface AppMetadata {
    val appId: String get() = "com.github.rgbpx.chan"
    val settingsFileName: String get() = "settings.json"
    val appName: String get() = "chan"
    val appTitle: String get() = "Chan"
    val minDefaultWidth: Int get() = 320
    val minDefaultHeight: Int get() = 480
}

expect val appMetadata: AppMetadata
