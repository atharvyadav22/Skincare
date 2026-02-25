package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductItemDTO(
    val brand: String,
    val category: String,
    val description: String,
    val id: Long,
    val name: String,
    val originalPrice: Double,
    val discountPrice: Double,
    val productAvailable: Boolean,
    val quantity: Int
)