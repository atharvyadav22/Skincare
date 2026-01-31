package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.UserProfileRequestDTO
import org.aystudios.skincare.data.remote.dto.UserProfileResponseDTO

interface UserRepository {

    suspend fun getUserProfile(): ApiResult<UserProfileResponseDTO>
    suspend fun updateUserProfile(user: UserProfileRequestDTO): ApiResult<Unit>
}