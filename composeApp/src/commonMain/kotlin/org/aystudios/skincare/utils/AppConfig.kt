package org.aystudios.skincare.utils

import androidx.compose.runtime.Composable
import org.aystudios.skincare.config.BuildKonfig
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.koin.compose.viewmodel.koinViewModel

object AppConfig {
    // TODO: Optimize Endpoints
    // Use BuildKonfig library or paste your API key here.
    val BASE_URL = BuildKonfig.API_KEY
    const val LOCAL_BASE_URL = "http://192.168.31.57:8080/api"
    val LOGIN = "/auth/login"
    val REFRESH = "/auth/refresh"
    val ALL_CATEGORIES = "/products/categories"
    val PRODUCT_BY_CATEGORY = "/products/category"

    val GET_USER_PROFILE = "/user"
    val UPDATE_USER_PROFILE = "/user"
    val CART = "/cart"
    val CHECKOUT = "/checkout"

    val FAVOURITES = "/favourites"
}
@Composable
fun customViewModel(): FavouritesViewModel {
    val customViewModel : FavouritesViewModel = koinViewModel()
    return customViewModel
}
