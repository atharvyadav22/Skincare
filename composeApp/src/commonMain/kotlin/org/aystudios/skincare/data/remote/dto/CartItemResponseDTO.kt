package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartItemResponseDTO(
    val productId: Long,
    val quantity: Int,
    val productName: String,
    val image: String? = null,
    val brand: String,
    val description: String,
    val originalPrice: Double,
    val discountPrice: Double
)