package com.github.rgbpx.chan.imageboard.engine

import io.ktor.client.HttpClient

abstract class BaseImageboardEngine(
    protected val httpClient: HttpClient,
) : ImageboardEngine
