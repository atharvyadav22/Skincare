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
import org.aystudios.skincare.utils.BaseUrlRefresher

class CartApi(val client: HttpClient, val baseUrl: BaseUrlRefresher) {

    suspend fun getCartItems(): CartResponseDTO{
        return client.get("${baseUrl.getBaseUrl()}${CART}").body<CartResponseDTO>()
    }

    suspend fun addToCartItem(cartRequestDTO: CartRequestDTO): CartResponseDTO{
        return client.post("${baseUrl.getBaseUrl()}$CART"){
            setBody(cartRequestDTO)
        }.body<CartResponseDTO>()
    }

    suspend fun deleteCartItem(productId: Long): CartResponseDTO {
        return client.delete("${baseUrl.getBaseUrl()}$CART/$productId").body<CartResponseDTO>()
    }

    suspend fun checkout(): CartCheckoutResponseDTO{
        return client.get("${baseUrl.getBaseUrl()}$CART$CHECKOUT").body<CartCheckoutResponseDTO>()
    }
}