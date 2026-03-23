package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.aystudios.skincare.data.remote.dto.CartCheckoutResponseDTO
import org.aystudios.skincare.data.remote.dto.CartRequestDTO
import org.aystudios.skincare.data.remote.dto.CartResponseDTO
import org.aystudios.skincare.utils.AppConfig.CART
import org.aystudios.skincare.utils.AppConfig.CHECKOUT

class CartApi(val client: HttpClient, val baseUrl: String) {

    suspend fun getCartItems(): CartResponseDTO{
        return client.get("$baseUrl${CART}").body<CartResponseDTO>()
    }

    suspend fun addToCartItem(cartRequestDTO: CartRequestDTO): CartResponseDTO{
        return client.post("$baseUrl$CART"){
            setBody(cartRequestDTO)
        }.body<CartResponseDTO>()
    }

    suspend fun deleteCartItem(productId: Long): CartResponseDTO {
        return client.delete("$baseUrl$CART/$productId").body<CartResponseDTO>()
    }

    suspend fun checkout(): CartCheckoutResponseDTO{
        return client.get("$baseUrl$CART$CHECKOUT").body<CartCheckoutResponseDTO>()
    }
}