package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDTO(
    val email: String,
    val name: String,
    val phoneNo: String,
    val address: String,
    val profilePicUrl: String? = null
)
