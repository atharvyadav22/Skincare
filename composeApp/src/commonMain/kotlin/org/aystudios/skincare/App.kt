package org.aystudios.skincare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.aystudios.skincare.presentation.screens.DetailsScreenNavigator
import org.aystudios.skincare.presentation.screens.LoginSignUpScreenNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Scaffold {
        Box(modifier = Modifier.fillMaxSize().padding(it)){

            Navigator(DetailsScreenNavigator){
                SlideTransition(it)
            }
        }
    }
}