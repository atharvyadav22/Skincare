package org.aystudios.skincare.core.network

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings


private const val ANDROID_SETTINGS_NAME = "android_settings"
actual class ProvideSettingsFactory(private val context: Context) {
    actual fun provideSettings(): Settings {
        val pref = context.getSharedPreferences(ANDROID_SETTINGS_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesSettings(pref)
    }
}