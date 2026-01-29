package org.aystudios.skincare.data.repository

import io.ktor.client.HttpClient
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.AuthApi
import org.aystudios.skincare.data.remote.dto.LoginRequestDTO
import org.aystudios.skincare.domain.AuthRepository
import org.aystudios.skincare.utils.AppLogger
import org.aystudios.skincare.utils.TokenStorage

class AuthRepositoryImpl(private val authApi: AuthApi, private val tokenStorage: TokenStorage, private val client: HttpClient): AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<Unit> {

        return safeApiCall{
            AppLogger.auth.i { "AuthRepositoryImpl: Login with email: $email and password: $password" }
            val response = authApi.login(LoginRequestDTO(email, password))
            AppLogger.auth.i { "AuthRepositoryImpl: Login response: $response" }
            tokenStorage.saveTokens(response.accessToken, response.refreshToken)
        }
    }



}