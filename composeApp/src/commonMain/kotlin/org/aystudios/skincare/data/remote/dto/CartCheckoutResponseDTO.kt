package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartCheckoutResponseDTO(
    val userProfile: UserProfileResponseDTO,
    val cart: CartResponseDTO,
    val paymentModes: List<PaymentMode>
)