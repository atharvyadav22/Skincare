package org.aystudios.skincare

import androidx.compose.ui.window.ComposeUIViewController
import org.aystudios.skincare.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController() : UIViewController {
    initKoin {
        printLogger()
    }
    // Wrap the Shared App into a standard iOS View Controller for Swift compatibility
    return ComposeUIViewController { App() }
}