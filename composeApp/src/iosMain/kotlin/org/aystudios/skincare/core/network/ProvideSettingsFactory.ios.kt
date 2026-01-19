package org.aystudios.skincare.core.network

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults


actual class ProvideSettingsFactory {
    actual fun provideSettings(): Settings {
        val pref = NSUserDefaults.standardUserDefaults
        return NSUserDefaultsSettings(pref)
    }
}