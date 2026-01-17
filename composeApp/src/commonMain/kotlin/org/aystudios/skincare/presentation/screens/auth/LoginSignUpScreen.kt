package org.aystudios.skincare.presentation.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.getAppNavigator
import org.aystudios.skincare.presentation.navigation.AppBottomNavigator
import org.aystudios.skincare.presentation.screens.auth.component.LoginSignUpScreenComponent
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.utils.LocalLoginViewModel
import org.aystudios.skincare.utils.LocalTokenStorage
import org.aystudios.skincare.utils.TokenStorage

object LoginSignUpScreenNavigator : Screen {
    @Composable
    override fun Content() {
        LoginSignUpScreen()
    }
}

@Composable
fun LoginSignUpScreen() {

    val viewModel = LocalLoginViewModel.current
    val tokenStorage = LocalTokenStorage.current
    val navigator = getAppNavigator()

    LaunchedEffect(Unit) {
        if (tokenStorage.getAccessToken() != null) {
            navigator.replaceAll(AppBottomNavigator)
        }
    }

    var isLoginScreen by remember { mutableStateOf(false) }
    AppScaffold(showTopBar = false) {

        LoginSignUpScreenComponent(viewModel, isLoginScreen, { isLoginScreen = !isLoginScreen })

    }

}





