package org.aystudios.skincare.data.repository

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.ProductApi
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.data.remote.dto.PageDTO
import org.aystudios.skincare.domain.ProductRepository
import org.aystudios.skincare.utils.AppLogger

class ProductRepositoryImpl(
    private val productApi: ProductApi
) : ProductRepository {
    override suspend fun getProductByCategory(category: String, page: Int): ApiResult<PageDTO> {
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

    override suspend fun getProductById(id: Long): ApiResult<ProductItemDTO> {
        return safeApiCall {
            AppLogger.products.i { "Getting product by id: $id" }
            val product = productApi.getProductByCategory(id)
            AppLogger.products.i { "Successfully got product by id: $product" }
            product
        }
    }

    override suspend fun getSearchProducts(query: String, page: Int): ApiResult<PageDTO> {
        return safeApiCall {
            AppLogger.products.i { "Getting search products by query: $query" }
            val products = productApi.searchProducts(query, page)
            AppLogger.products.i { "Successfully got search products: $products" }
            products
        }
    }
}