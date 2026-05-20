package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemResponseDTO(
    val productId: Long,
    val productName: String,
    val brand: String,
    val image: String? = null,
    val quantity: Int,
    val priceAtPurchase: Double
)
