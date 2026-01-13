package org.aystudios.skincare

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.aystudios.skincare.core.network.ProvideSettingsFactory

fun main() = application {
    val settings = ProvideSettingsFactory().provideSettings()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Skincare",
    ) {
        App(settings)
    }
}