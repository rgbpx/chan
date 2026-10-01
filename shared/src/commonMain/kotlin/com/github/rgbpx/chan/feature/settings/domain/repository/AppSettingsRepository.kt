package com.github.rgbpx.chan.feature.settings.domain.repository

import com.github.rgbpx.chan.feature.settings.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val settings: Flow<AppSettings>
    suspend fun setFirstLaunchCompleted()
    fun readCorruptedFileContent(corruptedFileName: String): Flow<String>
    suspend fun resetCorruptedFile()
}
