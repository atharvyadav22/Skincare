package org.aystudios.skincare.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private const val ANDROID_SETTINGS_NAME = "android_settings"
actual val platformSettingsModule: Module = module {
    single<Settings> {
        val context = androidContext()
        val pref = context.getSharedPreferences(ANDROID_SETTINGS_NAME, Context.MODE_PRIVATE)
        SharedPreferencesSettings(pref)
    }
}