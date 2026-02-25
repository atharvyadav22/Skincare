package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.data.remote.dto.PageDTO

interface ProductRepository {
    suspend fun getProductByCategory(category: String, page: Int): ApiResult<PageDTO>
    suspend fun getAllCategories(): ApiResult<List<String>>

    suspend fun getProductById(id: Long): ApiResult<ProductItemDTO>

    suspend fun getSearchProducts(query: String, page: Int): ApiResult<PageDTO>


}