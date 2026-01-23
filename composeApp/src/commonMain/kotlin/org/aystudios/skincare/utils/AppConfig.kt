package org.aystudios.skincare.utils

import org.aystudios.skincare.config.BuildKonfig

object AppConfig {

    val BASE_URL = BuildKonfig.API_KEY
    val LOGIN = "$BASE_URL/auth/login"
    val REFRESH = "$BASE_URL/auth/refresh"
    val ALL_CATEGORIES = "$BASE_URL/products/categories"
    val PRODUCT_BY_CATEGORY = "$BASE_URL/products/category"
}