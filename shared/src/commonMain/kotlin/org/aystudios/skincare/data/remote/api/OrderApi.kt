package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.aystudios.skincare.data.remote.dto.MyOrderResponseDTO
import org.aystudios.skincare.data.remote.dto.OrderRequestDTO
import org.aystudios.skincare.data.remote.dto.OrderResponseDTO
import org.aystudios.skincare.utils.AppConfig.ORDER
import org.aystudios.skincare.utils.BaseUrlRefresher

class OrderApi(val client: HttpClient, val baseUrl: BaseUrlRefresher) {

    suspend fun createOrder(orderRequestDTO: OrderRequestDTO): OrderResponseDTO{
        return client.post("${baseUrl.getBaseUrl()}$ORDER"){
            setBody(orderRequestDTO)
        }.body<OrderResponseDTO>()

    }

    suspend fun getOrders(): List<MyOrderResponseDTO>{
        return client.get("${baseUrl.getBaseUrl()}$ORDER").body()
    }
}