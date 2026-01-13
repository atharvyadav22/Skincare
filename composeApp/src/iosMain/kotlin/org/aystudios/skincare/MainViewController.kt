package org.aystudios.skincare

import androidx.compose.ui.window.ComposeUIViewController
import org.aystudios.skincare.core.network.ProvideSettingsFactory

fun MainViewController() = ComposeUIViewController {

    val settings = ProvideSettingsFactory().provideSettings()
    App(settings)
}