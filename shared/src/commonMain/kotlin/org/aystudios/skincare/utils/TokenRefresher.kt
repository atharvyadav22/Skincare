package org.aystudios.skincare.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.aystudios.skincare.data.remote.api.AuthApi
import org.aystudios.skincare.data.remote.dto.RefreshRequestDTO

class TokenRefresher(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage
) {

    private val mutex = Mutex()
    suspend fun refreshToken(): String? {
        return mutex.withLock {
            AppLogger.auth.i { "Refreshing access token…" }
            val refreshToken = tokenStorage.getRefreshToken() ?: run {
                    AppLogger.auth.e { "No refresh token found" }
                    return null
                }

            try {
                val response = authApi.refresh(RefreshRequestDTO(refreshToken))

                tokenStorage.saveTokens(
                    response.accessToken,
                    response.refreshToken
                )
                AppLogger.auth.i { "Token refresh successful" }

                response.accessToken
            } catch (e: Exception) {
                AppLogger.auth.e(e) { "Token refresh FAILED with error: ${e.message}" }
                tokenStorage.clear()
                null
            }
        }
    }
}
