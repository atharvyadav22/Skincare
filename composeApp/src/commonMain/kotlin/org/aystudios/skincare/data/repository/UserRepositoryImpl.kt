package org.aystudios.skincare.data.repository

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.UserApi
import org.aystudios.skincare.data.remote.dto.UserProfileRequestDTO
import org.aystudios.skincare.data.remote.dto.UserProfileResponseDTO
import org.aystudios.skincare.domain.UserRepository
import org.aystudios.skincare.utils.AppLogger

class UserRepositoryImpl(val userApi: UserApi): UserRepository {
    override suspend fun getUserProfile(): ApiResult<UserProfileResponseDTO> {
        return safeApiCall {
            AppLogger.users.i{"Getting user profile"}
            val user = userApi.getUserProfile()
            AppLogger.users.i{"Successfully got user profile: $user"}
            user
        }
    }

    override suspend fun updateUserProfile(user: UserProfileRequestDTO): ApiResult<UserProfileRequestDTO> {
        return safeApiCall {
            AppLogger.users.i { "Updating user profile" }
            val updatedUser = userApi.updateUserProfile(user)
            AppLogger.users.i { "Successfully updated user profile: $updatedUser" }
            updatedUser
        }
    }
}