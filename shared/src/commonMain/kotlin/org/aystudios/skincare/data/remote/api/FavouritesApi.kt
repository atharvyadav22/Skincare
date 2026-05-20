package org.aystudios.skincare.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import org.aystudios.skincare.data.remote.dto.FavouriteResponseDTO
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.utils.AppConfig.FAVOURITES
import org.aystudios.skincare.utils.BaseUrlRefresher

class FavouritesApi(val client: HttpClient, val baseUrl: BaseUrlRefresher) {

    suspend fun getAllFavourites(): List<ProductItemDTO>{
        return client.get("${baseUrl.getBaseUrl()}$FAVOURITES").body<List<ProductItemDTO>>()
    }

    suspend fun isFavourite(productId: Long): FavouriteResponseDTO{
        return client.get("${baseUrl.getBaseUrl()}$FAVOURITES/$productId").body()
    }

    suspend fun toggleFavourite(productId: Long): FavouriteResponseDTO{
        return client.post("${baseUrl.getBaseUrl()}$FAVOURITES/$productId").body()
    }
}