package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartRequestDTO(
    val productId: Long,
    val quantity: Int
)
