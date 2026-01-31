package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.CategoryDTO

interface ProductRepository {
    suspend fun getProductByCategory(category: String, page: Int): ApiResult<CategoryDTO>
    suspend fun getAllCategories(): ApiResult<List<String>>

}