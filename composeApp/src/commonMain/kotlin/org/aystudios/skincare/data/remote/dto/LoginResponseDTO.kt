package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
