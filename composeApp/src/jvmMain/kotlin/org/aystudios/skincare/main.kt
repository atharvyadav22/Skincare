package org.aystudios.skincare

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.aystudios.skincare.di.initKoin

fun main() = application {

    initKoin { printLogger() }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Skincare",
    ) {
        App()
    }
}