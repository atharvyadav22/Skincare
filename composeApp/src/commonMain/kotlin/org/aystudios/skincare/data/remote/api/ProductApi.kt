package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.aystudios.skincare.data.remote.dto.CategoryDTO
import org.aystudios.skincare.utils.AppConfig.ALL_CATEGORIES
import org.aystudios.skincare.utils.AppConfig.PRODUCT_BY_CATEGORY

class ProductApi(private val client: HttpClient, private val baseUrl: String) {
    suspend fun getProductsByCategory(category: String, page: Int): CategoryDTO{
        return client.get("$baseUrl$PRODUCT_BY_CATEGORY/${category}"){
            parameter("page", page)
        }.body<CategoryDTO>()
    }

    suspend fun getAllCategories(): List<String>{
        return client.get("$baseUrl$ALL_CATEGORIES").body<List<String>>()
    }
}