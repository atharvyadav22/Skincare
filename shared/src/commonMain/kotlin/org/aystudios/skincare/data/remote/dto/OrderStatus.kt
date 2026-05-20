package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatus {
    CREATED,  CONFIRMED, COMPLETED, PACKED, SHIPPED, OUT_OF_DELIVERY, DELIVERED, CANCELLED

}