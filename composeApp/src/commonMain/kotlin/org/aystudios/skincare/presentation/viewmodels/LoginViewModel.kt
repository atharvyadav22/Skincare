package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.domain.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _loginState = MutableStateFlow<ApiResult<Unit>>(ApiResult.Idle)
    val loginState: StateFlow<ApiResult<Unit>> = _loginState

    fun login(email: String, password: String){
        viewModelScope.launch {
            _loginState.value = ApiResult.Loading

            when(val result = authRepository.login(email, password)){
                is ApiResult.Success -> {
                    _loginState.value = ApiResult.Success(Unit)
                }
                is ApiResult.Error -> {
                    _loginState.value = ApiResult.Error(result.message)
                }
                is ApiResult.Loading -> {
                    _loginState.value = ApiResult.Loading
                }
                is ApiResult.Idle -> {}
            }

        }
    }

}