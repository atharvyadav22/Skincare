package org.aystudios.skincare.utils

import com.russhwolf.settings.Settings

class TokenStorage(private val settings: Settings) {

    fun saveAccessToken(token: String, refresh: String) {
        settings.putString("access_token", token)
        settings.putString("refresh_token", refresh)
    }

    fun getAccessToken(): String? {
        return settings.getStringOrNull("access_token")
    }

    fun getRefreshToken(): String? {
        return settings.getStringOrNull("refresh_token")
    }

    fun clear() {
        settings.clear()
    }
}
