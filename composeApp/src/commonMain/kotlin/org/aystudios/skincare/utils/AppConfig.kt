package org.aystudios.skincare.utils

import org.aystudios.skincare.config.BuildKonfig

object AppConfig {
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

}