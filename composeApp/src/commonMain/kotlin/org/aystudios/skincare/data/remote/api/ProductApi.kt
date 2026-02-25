package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.data.remote.dto.PageDTO
import org.aystudios.skincare.utils.AppConfig.ALL_CATEGORIES
import org.aystudios.skincare.utils.AppConfig.PRODUCT_BY_CATEGORY

class ProductApi(private val client: HttpClient, private val baseUrl: String) {
    suspend fun getProductsByCategory(category: String, page: Int): PageDTO{
        return client.get("$baseUrl$PRODUCT_BY_CATEGORY/${category}"){
            parameter("page", page)
        }.body<PageDTO>()
    }

    suspend fun getAllCategories(): List<String>{
        return client.get("$baseUrl$ALL_CATEGORIES").body<List<String>>()
    }

    suspend fun getProductByCategory(id: Long): ProductItemDTO{
        return client.get("$baseUrl/products/${id}").body<ProductItemDTO>()
    }

    suspend fun searchProducts(query: String, page: Int): PageDTO{
        return client.get("$baseUrl/products/search"){
            parameter("keyword", query)
        }.body<PageDTO>()
    }
}