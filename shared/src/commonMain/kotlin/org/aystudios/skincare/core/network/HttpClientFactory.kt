package org.aystudios.skincare.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.aystudios.skincare.utils.AppConfig
import org.aystudios.skincare.utils.TokenRefresher
import org.aystudios.skincare.utils.TokenStorage

expect fun providesHttpEngine(): HttpClientEngine

fun providesAuthHttpClient(): HttpClient =
    HttpClient(providesHttpEngine()) {
        install(ContentNegotiation) { json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
        ) }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000      // ⏱️ total request
            connectTimeoutMillis = 90_000      // 🔌 TCP connect
            socketTimeoutMillis = 60_000       // 📡 data transfer
        }
    }

fun providesHttpClient(
    tokenStorage: TokenStorage,
    tokenRefresher: TokenRefresher
): HttpClient {

    return HttpClient(providesHttpEngine()) {

        expectSuccess = false

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }

        install(Auth) {
            bearer {

                loadTokens {
                    val access = tokenStorage.getAccessToken()
                    val refresh = tokenStorage.getRefreshToken()
                    if (access != null && refresh != null) {
                        BearerTokens(access, refresh)
                    } else null
                }

                refreshTokens {
                    val newAccessToken = tokenRefresher.refreshToken()

                    if (newAccessToken != null) {
                        BearerTokens(
                            accessToken = newAccessToken,
                            refreshToken = tokenStorage.getRefreshToken()!!
                        )
                    } else {
                        null
                    }
                }

                sendWithoutRequest { request ->
                    request.url.host == Url(AppConfig.BASE_URL).host
                }
            }
        }

        // Highly recommended for debugging your Spring Boot responses
        install(Logging) {
            level = LogLevel.INFO
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000      // ⏱️ total request
            connectTimeoutMillis = 90_000      // 🔌 TCP connect
            socketTimeoutMillis = 60_000       // 📡 data transfer
        }

    }
}


