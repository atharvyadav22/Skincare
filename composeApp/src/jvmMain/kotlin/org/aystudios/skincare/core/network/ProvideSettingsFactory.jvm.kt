package org.aystudios.skincare.core.network

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences


actual class ProvideSettingsFactory {
    actual fun provideSettings(): Settings {
        val pref = Preferences.userRoot()
        return PreferencesSettings(pref)
    }
}