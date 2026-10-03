package com.github.rgbpx.chan.imageboard.engine

import io.ktor.client.HttpClient

abstract class CommonImageboardEngine(
    httpClient: HttpClient,
) : BaseImageboardEngine(httpClient)
