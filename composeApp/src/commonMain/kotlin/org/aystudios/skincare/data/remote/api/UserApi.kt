package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import org.aystudios.skincare.data.remote.dto.UserProfileRequestDTO
import org.aystudios.skincare.data.remote.dto.UserProfileResponseDTO
import org.aystudios.skincare.utils.AppConfig.GET_USER_PROFILE
import org.aystudios.skincare.utils.AppConfig.UPDATE_USER_PROFILE

class UserApi(val client: HttpClient,private val baseUrl: String) {

    suspend fun getUserProfile(): UserProfileResponseDTO{
        return client.get("$baseUrl$GET_USER_PROFILE").body<UserProfileResponseDTO>()
    }

    suspend fun updateUserProfile(user: UserProfileRequestDTO): UserProfileRequestDTO{
        return client.put("$baseUrl$UPDATE_USER_PROFILE"){
           setBody(user)
        }.body<UserProfileRequestDTO>()
    }
}