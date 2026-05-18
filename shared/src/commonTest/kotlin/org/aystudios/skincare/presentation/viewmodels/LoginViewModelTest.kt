package org.aystudios.skincare.presentation.viewmodels

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.aystudios.skincare.core.dispatcher.TestDispatcherProvider
import org.aystudios.skincare.core.dispatcher.runTestWithDispatcher
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.domain.AuthRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val repository = mock<AuthRepository>()

    // ======== Helper ========
    private val email = "test@gmail.com"
    private val password = "123456"


    @Test
    fun login_whenSuccess_returnsSuccessState() = runTestWithDispatcher { dispatcher ->

        everySuspend { repository.login(any(), any()) } returns ApiResult.Success(Unit)

        val viewModel = LoginViewModel(repository, dispatcher)

        viewModel.login(email, password)

        advanceUntilIdle()

        val state = viewModel.loginState.value // This replace expectMostRecent() that .value

        assertTrue(state.isSuccess)

    }

    @Test
    fun login_whenError_returnsErrorState() = runTestWithDispatcher { dispatcher ->

        everySuspend { repository.login(any(), any()) } returns ApiResult.Error("Error")

        val viewModel = LoginViewModel(repository, dispatcher)

        viewModel.login(email, password)

        advanceUntilIdle()

        val state = viewModel.loginState.value

        assertEquals("Error", state.errorMessage)
    }

    @Test
    fun login_whenLoading_returnsLoadingState() = runTestWithDispatcher { dispatcher ->

        everySuspend { repository.login(any(), any()) } returns ApiResult.Success(Unit)

        val viewModel = LoginViewModel(repository, dispatcher)

        viewModel.loginState.test {
            skipItems(1)

            viewModel.login(email, password)

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            advanceUntilIdle()
            val state = viewModel.loginState.value
            assertTrue(state.isSuccess)

            cancelAndIgnoreRemainingEvents()
        }


    }

}
