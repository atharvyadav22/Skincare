package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.MyOrderResponseDTO
import org.aystudios.skincare.data.remote.dto.OrderRequestDTO
import org.aystudios.skincare.data.remote.dto.OrderResponseDTO
import org.aystudios.skincare.domain.OrderRepository

class OrderViewModel(val orderRepository: OrderRepository) : ViewModel() {

//    init {
//        getOrders()
//    }
    data class CreateOrderUIState(
        val orderResponseDTO: OrderResponseDTO? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _createOrder = MutableStateFlow(CreateOrderUIState())
    val createOrder = _createOrder.asStateFlow()

    fun createOrder(orderRequestDTO: OrderRequestDTO) {
        viewModelScope.launch {
            _createOrder.update {
                it.copy(isLoading = true, error = null)
            }

            when (val response = orderRepository.createOrder(orderRequestDTO)) {
                is ApiResult.Success -> {
                    _createOrder.update {
                        it.copy(orderResponseDTO = response.data, isLoading = false, error = null)
                    }
                    getOrders()
                }

                is ApiResult.Error ->{
                    _createOrder.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }
            }

        }
    }

    data class GetOrdersUIState(
        val myOrderResponseDTO: List<MyOrderResponseDTO> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _getOrders = MutableStateFlow(GetOrdersUIState())
    val getOrders = _getOrders.asStateFlow()

    fun getOrders(){
        viewModelScope.launch {
            _getOrders.update {
                it.copy(isLoading = true, error = null)
            }

            when(val response = orderRepository.getOrders()){
                is ApiResult.Success -> {
                    _getOrders.update {
                        it.copy(myOrderResponseDTO = response.data, isLoading = false, error = null)
                    }
                }
                is ApiResult.Error -> {
                    _getOrders.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }
            }
        }
    }

}