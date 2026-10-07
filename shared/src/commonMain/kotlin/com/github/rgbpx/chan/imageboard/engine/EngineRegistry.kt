package com.github.rgbpx.chan.imageboard.engine

import com.github.rgbpx.chan.app.di.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@Inject
class EngineRegistry(
    factories: Set<ImageboardEngineFactory>,
) {
    private val factoriesById = factories.associateBy { it.id }

    fun get(id: EngineId): ImageboardEngineFactory? {
        return factoriesById[id]
    }

    fun all(): Collection<ImageboardEngineFactory> {
        return factoriesById.values
    }
}
