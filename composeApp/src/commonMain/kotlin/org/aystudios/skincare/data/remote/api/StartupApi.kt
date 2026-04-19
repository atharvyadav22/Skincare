package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.aystudios.skincare.utils.AppConfig.TEST
import org.aystudios.skincare.utils.BaseUrlRefresher

class StartupApi(private val client: HttpClient, private val baseUrl: BaseUrlRefresher) {

    suspend fun startup(): String{
        return client.get("${baseUrl.getBaseUrl()}$TEST").body()
    }
}