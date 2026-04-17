package org.aystudios.skincare.core.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class TestDispatcherProvider(dispatcher: TestDispatcher) : AppDispatcherProvider {
    override val main = dispatcher
    override val io = dispatcher
}

@OptIn(ExperimentalCoroutinesApi::class)
fun runTestWithDispatcher(block: suspend TestScope.(TestDispatcherProvider) -> Unit) = runTest {
    val dispatcher = StandardTestDispatcher(testScheduler)
    val provider = TestDispatcherProvider(dispatcher)

    Dispatchers.setMain(dispatcher)

    try {
        block(provider)
    } finally {
        Dispatchers.resetMain()
    }
}