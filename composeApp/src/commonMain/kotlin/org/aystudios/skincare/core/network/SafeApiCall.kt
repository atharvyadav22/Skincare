package org.aystudios.skincare.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import org.aystudios.skincare.data.remote.dto.ErrorDTO
import org.aystudios.skincare.utils.TokenStorage

suspend fun <T> safeApiCall(
    client: HttpClient,
    storage: TokenStorage,
    allowRefresh: Boolean = true,
    apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: ClientRequestException) {

        // 🔁 ONLY for protected APIs
        if (
            allowRefresh &&
            e.response.status.value == 401 &&
            storage.getRefreshToken() != null
        ) {
            val refreshed = try {
                refreshToken(client, storage)
                true
            } catch (_: Exception) {
                false
            }

            if (refreshed) {
                ApiResult.Success(apiCall())
            } else {
                ApiResult.Error("Session expired. Please login again.")
            }

        } else {
            // ✅ READ BACKEND ERROR
            val body = e.response.bodyAsText()
            val message = extractMessage(body)
            ApiResult.Error(message)
        }

    } catch (e: Throwable) {
        ApiResult.Error(e.message ?: "Something went wrong")
    }
}



fun extractMessage(body: String): String {
    return try {
        Json.decodeFromString<ErrorDTO>(body).message
    } catch (_: Exception) {
        "Something went wrong"
    }
}