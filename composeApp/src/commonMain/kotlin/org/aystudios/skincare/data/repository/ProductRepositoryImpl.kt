package org.aystudios.skincare.data.repository

import io.ktor.client.HttpClient
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.ProductApi
import org.aystudios.skincare.data.remote.dto.CategoryDTO
import org.aystudios.skincare.domain.ProductRepository
import org.aystudios.skincare.utils.AppLogger
import org.aystudios.skincare.utils.TokenStorage

class ProductRepositoryImpl(
    private val productApi: ProductApi,
    private val tokenStorage: TokenStorage,
    private val client: HttpClient
) : ProductRepository {
    override suspend fun getProductByCategory(category: String, page: Int): ApiResult<CategoryDTO> {
        return safeApiCall {
            AppLogger.products.i { "Getting products by category: $category" }
            val products = productApi.getProductsByCategory(category, page)
            AppLogger.products.i { "Successfully got products by category: $category" }
            products
        }
    }

    override suspend fun getAllCategories(): ApiResult<List<String>> {
        return safeApiCall {
            AppLogger.products.i { "Getting all categories" }
            val categories = productApi.getAllCategories()
            AppLogger.products.i { "Successfully got all categories" }
            categories
        }
    }
}