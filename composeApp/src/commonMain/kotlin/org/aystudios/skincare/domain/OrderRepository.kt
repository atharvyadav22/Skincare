package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.MyOrderResponseDTO
import org.aystudios.skincare.data.remote.dto.OrderRequestDTO
import org.aystudios.skincare.data.remote.dto.OrderResponseDTO

interface OrderRepository {

    suspend fun createOrder(orderRequestDTO: OrderRequestDTO): ApiResult<OrderResponseDTO>

    suspend fun getOrders(): ApiResult<List<MyOrderResponseDTO>>


}