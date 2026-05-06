package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.domain.StartupRepository

data class StartUpUiState(val response: String = "", val isLoading: Boolean = false, val error: String? = null)

class StartupViewModel(val repository: StartupRepository): ViewModel() {
    private val _startUpState = MutableStateFlow(StartUpUiState())
    val startUpState = _startUpState.asStateFlow()

    init {
        test()
    }

    fun test(){
        viewModelScope.launch {
            _startUpState.update {
                it.copy(isLoading = true, error = null)
            }

            when(val response = repository.test()){
                is ApiResult.Success -> {
                    _startUpState.update {
                        it.copy(response = response.data, isLoading = false, error = null)
                    }
                }

                is ApiResult.Error -> {
                    _startUpState.update {
                        it.copy(error = response.message, isLoading = false)
                    }
                }
            }
        }
    }

}