package com.github.rgbpx.chan.imageboard.site.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.core.okio.OkioStorage
import com.github.rgbpx.chan.app.data.appMetadata
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.filesDir
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.resolve
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.time.Clock

internal fun createSitesDataStore(
    fileName: String = appMetadata.sitesFileName,
): DataStore<StoredSites> {
    fun pathOf(name: String) = FileKit.filesDir.resolve(name).path.toPath()

    return DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = StoredSitesSerializer,
            producePath = { pathOf(fileName) },
        ),
        corruptionHandler = ReplaceFileCorruptionHandler {
            val unixTimeMs = Clock.System.now().toEpochMilliseconds()
            FileSystem.SYSTEM.copy(
                source = pathOf(fileName),
                target = pathOf("$fileName.backup-$unixTimeMs"),
            )
            StoredSites()
        },
    )
}
