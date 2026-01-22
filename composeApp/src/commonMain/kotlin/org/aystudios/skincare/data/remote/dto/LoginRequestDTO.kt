package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDTO(val email: String, val password: String)
