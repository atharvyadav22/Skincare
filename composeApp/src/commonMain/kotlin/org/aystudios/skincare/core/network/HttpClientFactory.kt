package org.aystudios.skincare.core.network

import io.ktor.client.HttpClient
import org.aystudios.skincare.utils.TokenStorage

expect fun createAuthHttpClient(): HttpClient
expect fun createHttpClient(tokenStorage: TokenStorage): HttpClient