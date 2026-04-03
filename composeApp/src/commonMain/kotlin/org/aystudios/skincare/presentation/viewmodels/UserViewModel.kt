package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.dispatchers.AppDispatcherProvider
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.UserProfileRequestDTO
import org.aystudios.skincare.data.remote.dto.UserProfileResponseDTO
import org.aystudios.skincare.domain.UserRepository

data class UserProfileUiState(
    val response: UserProfileResponseDTO? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class UserViewModel(val repository: UserRepository, val dispatcherProvider: AppDispatcherProvider) : ViewModel() {

    private val _userProfile = MutableStateFlow(UserProfileUiState())
    val userProfile = _userProfile.asStateFlow()

    init {
        getUserProfile()
    }

    fun getUserProfile() {
        viewModelScope.launch(dispatcherProvider.main) {
            _userProfile.value =
                UserProfileUiState(isLoading = true)

            when (val result = repository.getUserProfile()) {
                is ApiResult.Success -> {
                    _userProfile.value = UserProfileUiState(response = result.data)
                }

                is ApiResult.Error -> {
                    _userProfile.value = UserProfileUiState(error = result.message)
                }

                else -> Unit
            }
        }
    }


    data class ProfileRequestUiState(
        val isSuccess: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _updateUserProfile = MutableStateFlow(ProfileRequestUiState())
    val updateUserProfile = _updateUserProfile.asStateFlow()

    fun updateUserProfile(user: UserProfileRequestDTO) {
        viewModelScope.launch {

            _updateUserProfile.update { it.copy(isLoading = true, error = null) }


            when (val result = repository.updateUserProfile(user)) {
                is ApiResult.Success -> {

                    _updateUserProfile.update {
                        it.copy(isSuccess = true, isLoading = false)
                    }
                    getUserProfile()
                }

                is ApiResult.Error -> {
                    _updateUserProfile.update {
                        it.copy(error = result.message, isLoading = false)
                    }
                }

                else -> Unit

            }
        }
    }

    fun resetUpdateProfileState() {
        _updateUserProfile.value = ProfileRequestUiState()
    }

}