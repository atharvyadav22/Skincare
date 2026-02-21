package org.aystudios.skincare.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDTO(
    val email: String,
    val name: String? = null,
    val phoneNo: String? = null,
    val address: String? = null,
    val profilePicUrl: String? = null
)
