package org.aystudios.skincare.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface AppDispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}

class DefaultDispatcherProvider : AppDispatcherProvider {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}