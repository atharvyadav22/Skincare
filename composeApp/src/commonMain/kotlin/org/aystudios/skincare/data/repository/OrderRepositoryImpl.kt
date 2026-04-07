package org.aystudios.skincare.data.repository

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.OrderApi
import org.aystudios.skincare.data.remote.dto.MyOrderResponseDTO
import org.aystudios.skincare.data.remote.dto.OrderRequestDTO
import org.aystudios.skincare.data.remote.dto.OrderResponseDTO
import org.aystudios.skincare.domain.OrderRepository
import org.aystudios.skincare.utils.AppLogger

class OrderRepositoryImpl(val orderApi: OrderApi): OrderRepository {
    override suspend fun createOrder(orderRequestDTO: OrderRequestDTO): ApiResult<OrderResponseDTO> {
        return safeApiCall {
            AppLogger.order.i { "Creating order $orderRequestDTO" }
            val result = orderApi.createOrder(orderRequestDTO)
            AppLogger.order.i { "Successfully created order: $result" }
            result
        }
    }

    override suspend fun getOrders(): ApiResult<List<MyOrderResponseDTO>> {
        return safeApiCall {
            AppLogger.order.i { "Getting orders" }
            val result = orderApi.getOrders()
            AppLogger.order.i { "Successfully got orders: $result" }
            result

        }
    }
}