package org.aystudios.skincare.di

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

actual val platformSettingsModule: Module = module {
    single<Settings>{
        val pref = Preferences.userRoot()
        PreferencesSettings(pref)
    }
}
