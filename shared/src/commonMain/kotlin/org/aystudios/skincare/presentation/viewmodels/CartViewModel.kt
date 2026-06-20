package org.aystudios.skincare.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.CartCheckoutResponseDTO
import org.aystudios.skincare.data.remote.dto.CartItemResponseDTO
import org.aystudios.skincare.data.remote.dto.CartRequestDTO
import org.aystudios.skincare.domain.CartRepository

data class CartUIState(
    val cartItems: List<CartItemResponseDTO> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class CartCheckoutResponseUIState(
    val cartItems: CartCheckoutResponseDTO? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class CartViewModel(val cartRepository: CartRepository) : ViewModel() {

    private val _cartState = MutableStateFlow(CartUIState())
    val cartState = _cartState.asStateFlow()

    init {
        getCartItems()
//        checkout()
    }

    fun getCartItems() {
        viewModelScope.launch {

            _cartState.update {
                it.copy(isLoading = true, error = null)
            }
            when (val response = cartRepository.getCartItems()) {

                is ApiResult.Success -> {
                    _cartState.update {
                        it.copy(
                            isLoading = false,
                            cartItems = response.data.cartItems,
                            totalPrice = response.data.totalPrice,
                            error = null
                        )
                    }
                }

                is ApiResult.Error -> {
                    _cartState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }

            }

        }
    }


    fun addToCart(cartRequestDTO: CartRequestDTO) {
        viewModelScope.launch {

            _cartState.update {
                it.copy(isLoading = true, error = null)
            }
            when (val response = cartRepository.addToCartItem(cartRequestDTO)) {
                is ApiResult.Success -> {

                    val result = response.data

                    _cartState.update { current ->
                        current.copy(
                            cartItems = result.cartItems,
                            totalPrice = result.totalPrice,
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is ApiResult.Error -> {
                    _cartState.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }

            }
        }
    }

    fun deleteCartItem(productId: Long) {
        viewModelScope.launch {


            when (val response = cartRepository.deleteCartItem(productId)) {
                is ApiResult.Success -> {
                    val result = response.data

                    _cartState.update { current ->
                        current.copy(
                            cartItems = result.cartItems,
                            totalPrice = result.totalPrice,
                            error = null
                        )
                    }
                }

                is ApiResult.Error -> {
                    _cartState.update {
                        it.copy(error = response.message)
                    }

                }

            }
        }
    }

    private val _checkout = MutableStateFlow(CartCheckoutResponseUIState())
    val checkout = _checkout.asStateFlow()

    fun checkout() {
        _checkout.update {
            it.copy(isLoading = true, error = null)
        }
        viewModelScope.launch {
            when (val response = cartRepository.checkout()) {
                is ApiResult.Success -> {
                    _checkout.update {
                        it.copy(cartItems = response.data, isLoading = false, error = null)
                    }
                }

                is ApiResult.Error -> {
                    _checkout.update {
                        it.copy(isLoading = false, error = response.message)
                    }
                }
            }

        }
    }


}