package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.CartCheckoutResponseDTO
import org.aystudios.skincare.data.remote.dto.CartRequestDTO
import org.aystudios.skincare.data.remote.dto.CartResponseDTO

interface CartRepository {

    suspend fun getCartItems(): ApiResult<CartResponseDTO>

    suspend fun addToCartItem(cartRequestDTO: CartRequestDTO): ApiResult<CartResponseDTO>

    suspend fun deleteCartItem(productId: Long): ApiResult<CartResponseDTO>

    suspend fun checkout(): ApiResult<CartCheckoutResponseDTO>
}