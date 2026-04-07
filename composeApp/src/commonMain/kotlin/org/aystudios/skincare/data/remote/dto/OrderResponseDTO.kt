package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseDTO(val orderId: Long, val status: OrderStatus, val paymentMode: PaymentMode, val price: Double)
