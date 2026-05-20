package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartResponseDTO(
    val cartItems: List<CartItemResponseDTO>,
    val totalPrice: Double
)
