package com.github.rgbpx.chan.feature.settings.domain

import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun setFirstLaunchCompleted()
    suspend fun readCorruptedFileContent(corruptedFileName: String): String
    suspend fun resetCorruptedFile()
}
