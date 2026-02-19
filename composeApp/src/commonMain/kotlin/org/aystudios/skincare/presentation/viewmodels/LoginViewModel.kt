package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.domain.AuthRepository

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String = ""
)

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {

            _loginState.value = LoginUiState(isLoading = true)

            when (val result = authRepository.login(email, password)) {
                is ApiResult.Success -> {
                    _loginState.value = LoginUiState(isSuccess = true)
                }

                is ApiResult.Error -> {
                    _loginState.value = LoginUiState(errorMessage = result.message)
                }

                else -> Unit
            }

        }
    }

}