package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val brand: String,
    val category: String,
    val description: String,
    val id: Int,
    val name: String,
    val originalPrice: Double,
    val discountPrice: Double,
    val productAvailable: Boolean,
    val quantity: Int
)