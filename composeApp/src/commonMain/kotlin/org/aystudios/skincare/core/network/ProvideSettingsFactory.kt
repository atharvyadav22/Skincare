package org.aystudios.skincare.core.network

import com.russhwolf.settings.Settings

expect class ProvideSettingsFactory {
    fun provideSettings(): Settings
}

