package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequestDTO(val paymentMode: PaymentMode)