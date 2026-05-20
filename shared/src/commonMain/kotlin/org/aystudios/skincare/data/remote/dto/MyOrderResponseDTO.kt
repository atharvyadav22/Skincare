package org.aystudios.skincare.data.remote.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MyOrderResponseDTO(
    val orderId: Long,
    val paymentMode: PaymentMode,
    val status: OrderStatus,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val orderItems: List<OrderItemResponseDTO>
)
