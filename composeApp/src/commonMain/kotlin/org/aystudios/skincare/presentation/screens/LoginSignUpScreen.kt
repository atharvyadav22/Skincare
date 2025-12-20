package org.aystudios.skincare.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.LoginSignUpScreenComponent
import org.aystudios.skincare.ui.theme.AppScaffold
import org.jetbrains.compose.ui.tooling.preview.Preview

object LoginSignUpScreenNavigator : Screen {
    @Composable
    override fun Content() {
        LoginSignUpScreen()
    }
}

@Preview
@Composable
fun LoginSignUpScreen() {

    var isLoginScreen by remember { mutableStateOf(false) }

    AppScaffold(showTopBar = false) {

        LoginSignUpScreenComponent(isLoginScreen, { isLoginScreen = !isLoginScreen })

    }

}





