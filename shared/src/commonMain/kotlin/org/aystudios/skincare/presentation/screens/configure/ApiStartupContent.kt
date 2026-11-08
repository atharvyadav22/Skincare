package org.aystudios.skincare.presentation.screens.configure

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.aystudios.skincare.presentation.components.getAppNavigator
import org.aystudios.skincare.presentation.navigation.AppRoute
import org.aystudios.skincare.presentation.viewmodels.StartupViewModel
import org.koin.compose.viewmodel.koinViewModel

/**
 * Route / Screen Composable.
 *
 * Responsibilities:
 * - Scoped dependency injection (ViewModel).
 * - Lifecycle-aware state observation (`collectAsStateWithLifecycle`).
 * - Side-effect collection (navigation events).
 * - Bridge between presentation events and business logic.
 * - Delegates rendering entirely to stateless [ApiStartupScreenUI].
 */
@Composable
fun ApiStartupContent(
    viewModel: StartupViewModel = koinViewModel()
) {
    val navigator = getAppNavigator()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Dispatch server status check intent when screen is first presented
    LaunchedEffect(Unit) {
        viewModel.onAction(StartupUiAction.CheckServerStatus)
    }

    // Collect one-off side effects (e.g. Navigation)
    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                StartupSideEffect.NavigateToLogin -> navigator.replaceAll(AppRoute.LoginSignUp)
            }
        }
    }

    ApiStartupScreenUI(
        state = uiState,
        onAction = viewModel::onAction
    )
}