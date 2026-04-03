package org.aystudios.skincare.core.network

import io.ktor.client.plugins.ResponseException
import org.aystudios.skincare.utils.AppLogger

suspend inline fun <T> safeApiCall(
    crossinline block: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: ResponseException) {
        AppLogger.network.e(e) { "API error ${e.response.status.value}" }
        ApiResult.Error(e.response.status.description)
    } catch (e: Throwable) {
        AppLogger.network.e(e) { "Unknown Error ${e.message}" }
        ApiResult.Error(e.message ?: "Unknown Error")
    }
}

