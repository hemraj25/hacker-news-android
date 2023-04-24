package com.hemraj.hackernews.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object AppDispatchers : DispatcherProvider {
    override val Default: CoroutineDispatcher by lazy { Dispatchers.Default }
    override val IO: CoroutineDispatcher by lazy { Dispatchers.IO }
    override val Main: CoroutineDispatcher by lazy { Dispatchers.Main }
}

interface DispatcherProvider {
    val Default: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
}
