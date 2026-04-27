package org.aystudios.skincare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.aystudios.skincare.presentation.screens.configure.ApiStartupScreenNavigator

@Composable
fun App() {

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Navigator(ApiStartupScreenNavigator) {
                SlideTransition(it)
            }
        }
    }
}
