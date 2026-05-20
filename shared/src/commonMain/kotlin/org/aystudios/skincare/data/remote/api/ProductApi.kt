package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.data.remote.dto.PageDTO
import org.aystudios.skincare.utils.AppConfig.ALL_CATEGORIES
import org.aystudios.skincare.utils.AppConfig.PRODUCT_BY_CATEGORY
import org.aystudios.skincare.utils.BaseUrlRefresher

class ProductApi(private val client: HttpClient, private val baseUrl: BaseUrlRefresher) {
    suspend fun getProductsByCategory(category: String, page: Int): PageDTO{
        return client.get("${baseUrl.getBaseUrl()}$PRODUCT_BY_CATEGORY/${category}"){
            parameter("page", page)
        }.body<PageDTO>()
    }

    suspend fun getAllCategories(): List<String>{
        return client.get("${baseUrl.getBaseUrl()}$ALL_CATEGORIES").body<List<String>>()
    }

    suspend fun getProductByCategory(id: Long): ProductItemDTO{
        return client.get("${baseUrl.getBaseUrl()}/products/${id}").body<ProductItemDTO>()
    }

    suspend fun searchProducts(query: String, page: Int): PageDTO{
        return client.get("${baseUrl.getBaseUrl()}/products/search"){
            parameter("keyword", query)
        }.body<PageDTO>()
    }
}