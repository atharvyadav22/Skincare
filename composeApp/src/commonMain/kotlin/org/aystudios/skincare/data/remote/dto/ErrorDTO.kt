package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorDTO(
    val status: Int,
    val code: String,
    val message: String
)
