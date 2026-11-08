package org.aystudios.skincare.presentation.screens.configure

/**
 * Immutable UI State representing the state of the Startup Screen.
 */
data class StartupUiState(
    val response: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val savedLocalUrl: String? = null
)

/**
 * UI Actions (User Intents) sent from the Screen / Content to the ViewModel.
 */
sealed interface StartupUiAction {
    data object CheckServerStatus : StartupUiAction
    data class SaveCustomUrl(val url: String) : StartupUiAction
}

/**
 * One-time Side Effects (e.g. Navigation) emitted from ViewModel to UI.
 */
sealed interface StartupSideEffect {
    data object NavigateToLogin : StartupSideEffect
}

