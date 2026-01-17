package org.aystudios.skincare.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.aystudios.skincare.utils.TokenStorage

actual fun createAuthHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
        }
        defaultRequest {
            contentType(ContentType.Application.Json)

        }

        install(Logging) {
            level = LogLevel.BODY

        }
    }
}

actual fun createHttpClient(tokenStorage: TokenStorage): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
        install(DefaultRequest) {
            contentType(ContentType.Application.Json)
            headers{
                tokenStorage.getAccessToken()?.let {
                    append("Authorization", "Bearer $it")
                }
            }
        }

        install(Logging) {
            level = LogLevel.BODY

        }
    }
}