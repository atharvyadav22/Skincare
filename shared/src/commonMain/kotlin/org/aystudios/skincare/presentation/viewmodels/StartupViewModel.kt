package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.domain.StartupRepository
import org.aystudios.skincare.presentation.screens.configure.StartupSideEffect
import org.aystudios.skincare.presentation.screens.configure.StartupUiAction
import org.aystudios.skincare.presentation.screens.configure.StartupUiState
import org.aystudios.skincare.utils.BaseUrlRefresher


class StartupViewModel(
    private val repository: StartupRepository,
    private val baseUrlManager: BaseUrlRefresher
) : ViewModel() {

    // Initial state is computed synchronously without requiring coroutine launches in init
    private val _uiState = MutableStateFlow(
        StartupUiState(savedLocalUrl = baseUrlManager.getLocalBaseUrl())
    )
    val uiState: StateFlow<StartupUiState> = _uiState.asStateFlow()

    private val _sideEffect = Channel<StartupSideEffect>(Channel.BUFFERED)
    val sideEffect: Flow<StartupSideEffect> = _sideEffect.receiveAsFlow()

    /**
     * Single entry point for processing UI actions from the Screen.
     */
    fun onAction(action: StartupUiAction) {
        when (action) {
            StartupUiAction.CheckServerStatus -> checkServerStatus()
            is StartupUiAction.SaveCustomUrl -> onSaveCustomUrl(action.url)
        }
    }

    private fun checkServerStatus() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val response = repository.test()) {
                is ApiResult.Success -> {
                    _uiState.update {
                        it.copy(response = response.data, isLoading = false, error = null)
                    }
                    _sideEffect.send(StartupSideEffect.NavigateToLogin)
                }

                is ApiResult.Error -> {
                    _uiState.update {
                        it.copy(error = response.message, isLoading = false)
                    }
                }
            }
        }
    }

    private fun onSaveCustomUrl(url: String) {
        val formattedUrl = fixUrl(url)
        baseUrlManager.setLocalBaseUrl(formattedUrl)
        _uiState.update { it.copy(savedLocalUrl = formattedUrl) }
        checkServerStatus()
    }

    private fun fixUrl(url: String): String {
        val trimmed = url.trim()
        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            return trimmed
        }
        if (trimmed.contains(":")) {
            return "http://$trimmed"
        }
        return "http://$trimmed:8080/api"
    }
}
