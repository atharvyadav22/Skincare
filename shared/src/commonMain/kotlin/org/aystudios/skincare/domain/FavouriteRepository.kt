package org.aystudios.skincare.domain

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.data.remote.dto.FavouriteResponseDTO
import org.aystudios.skincare.data.remote.dto.ProductItemDTO

interface FavouriteRepository {

    suspend fun getAllFavourites(): ApiResult<List<ProductItemDTO>>

    suspend fun isFavourite(productId: Long): ApiResult<FavouriteResponseDTO>

    suspend fun toggleFavourite(productId: Long): ApiResult<FavouriteResponseDTO>
}