package org.aystudios.skincare.data.repository

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.CartApi
import org.aystudios.skincare.data.remote.dto.CartCheckoutResponseDTO
import org.aystudios.skincare.data.remote.dto.CartRequestDTO
import org.aystudios.skincare.data.remote.dto.CartResponseDTO
import org.aystudios.skincare.domain.CartRepository
import org.aystudios.skincare.utils.AppLogger

class CartRepositoryImpl(val cartApi: CartApi) : CartRepository {
    override suspend fun getCartItems(): ApiResult<CartResponseDTO> {
        return safeApiCall {
            AppLogger.cart.i { "Getting cart items" }
            val response = cartApi.getCartItems()
            AppLogger.cart.i { "Successfully got cart items" }
            response
        }
    }

    override suspend fun addToCartItem(cartRequestDTO: CartRequestDTO): ApiResult<CartResponseDTO> {
        return safeApiCall {
            AppLogger.cart.i { "Adding item to cart" }
            val response = cartApi.addToCartItem(cartRequestDTO)
            AppLogger.cart.i { "Successfully added item to cart: $response" }
            response
        }
    }

    override suspend fun deleteCartItem(productId: Long): ApiResult<CartResponseDTO> {
        return safeApiCall {
            AppLogger.cart.i { "Deleting cart item" }
            val response = cartApi.deleteCartItem(productId)
            AppLogger.cart.i { "Successfully deleted cart item" }
            response
        }
    }

    override suspend fun checkout(): ApiResult<CartCheckoutResponseDTO> {
        return safeApiCall {
            AppLogger.cart.i { "Checking out" }
            val response = cartApi.checkout()
            AppLogger.cart.i { "Successfully Got Checkout Response: $response" }
            response
        }
    }
}