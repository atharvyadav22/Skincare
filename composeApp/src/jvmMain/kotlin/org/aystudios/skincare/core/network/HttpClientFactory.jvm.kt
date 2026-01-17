package org.aystudios.skincare.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.aystudios.skincare.utils.TokenStorage

actual fun createAuthHttpClient(): HttpClient {
    return HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
        }
    }
}
actual fun createHttpClient(tokenStorage: TokenStorage): HttpClient {
    return HttpClient(CIO){

        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        headers {
            append("Content-Type", "application/json")
            append("Authorization", "Bearer ${tokenStorage.getAccessToken()}")
        }
    }
}