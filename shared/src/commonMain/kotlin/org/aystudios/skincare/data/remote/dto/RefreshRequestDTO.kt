package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDTO(val refreshToken: String)
