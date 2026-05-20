package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
enum class PaymentMode {
    CARD, NET_BANKING, UPI, COD
}