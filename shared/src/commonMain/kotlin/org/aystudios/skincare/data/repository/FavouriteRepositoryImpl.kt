package org.aystudios.skincare.data.repository

import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.core.network.safeApiCall
import org.aystudios.skincare.data.remote.api.FavouritesApi
import org.aystudios.skincare.data.remote.dto.FavouriteResponseDTO
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.domain.FavouriteRepository
import org.aystudios.skincare.utils.AppLogger

class FavouriteRepositoryImpl(val api: FavouritesApi) : FavouriteRepository {
    override suspend fun getAllFavourites(): ApiResult<List<ProductItemDTO>> {
        return safeApiCall {
            AppLogger.favourite.i { "Getting all favourites" }
            val response = api.getAllFavourites()
            AppLogger.favourite.i { "Got all favourites: $response" }
            response
        }
    }

    override suspend fun isFavourite(productId: Long): ApiResult<FavouriteResponseDTO> {
        return safeApiCall {
            AppLogger.favourite.i { "Checking if product is favourite" }
            val response = api.isFavourite(productId)
            AppLogger.favourite.i { "Checked if product is favourite: $response" }
            response
        }
    }

    override suspend fun toggleFavourite(productId: Long): ApiResult<FavouriteResponseDTO> {
        return safeApiCall {
            AppLogger.favourite.i { "Toggling favourite" }
            val response = api.toggleFavourite(productId)
            AppLogger.favourite.i { "Toggled favourite: $response" }
            response
        }
    }
}