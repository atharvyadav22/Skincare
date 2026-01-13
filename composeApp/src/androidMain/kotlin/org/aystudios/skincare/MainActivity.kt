package org.aystudios.skincare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.aystudios.skincare.core.network.ProvideSettingsFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val settingsFactory = ProvideSettingsFactory(this)
        val settings = settingsFactory.provideSettings()

        setContent {
            App(settings)
        }
    }
}