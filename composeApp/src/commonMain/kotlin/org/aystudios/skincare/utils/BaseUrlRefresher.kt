package org.aystudios.skincare.utils

import com.russhwolf.settings.Settings
import org.aystudios.skincare.utils.AppConfig.BASE_URL

class BaseUrlRefresher(private val settings: Settings) {

    private var overrideUrl: String? = null
    fun getBaseUrl(): String{
        return overrideUrl ?: BASE_URL
    }

    fun setLocalBaseUrl(baseUrl: String){
        overrideUrl = baseUrl
        settings.putString("local_url", baseUrl)
    }

    fun getLocalBaseUrl(): String?{
        return settings.getStringOrNull("local_url")
    }

    fun reset() {
        overrideUrl = null
    }


}