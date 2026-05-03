package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.aystudios.skincare.data.remote.dto.LoginRequestDTO
import org.aystudios.skincare.data.remote.dto.LoginResponseDTO
import org.aystudios.skincare.data.remote.dto.RefreshRequestDTO
import org.aystudios.skincare.utils.AppConfig.LOGIN
import org.aystudios.skincare.utils.AppConfig.REFRESH
import org.aystudios.skincare.utils.BaseUrlRefresher

class AuthApi(private val client: HttpClient, private val baseUrl: BaseUrlRefresher){

    suspend fun login(loginRequestDTO: LoginRequestDTO): LoginResponseDTO {
        return client.post("${baseUrl.getBaseUrl()}${LOGIN}"){
            setBody(loginRequestDTO)
        }.body()
    }
    suspend fun refresh(body: RefreshRequestDTO): LoginResponseDTO {
        return client.post("${baseUrl.getBaseUrl()}${REFRESH}") {
            setBody(body)
        }.body()
    }

}