package org.aystudios.skincare

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.aystudios.skincare.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

// To survive Activity Recreations and prevent Memory Leaks.
class SkincareApp : Application(){
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@SkincareApp)
            androidLogger()
        }
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}