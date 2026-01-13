package org.aystudios.skincare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import org.aystudios.skincare.core.network.createHttpClient
import org.aystudios.skincare.data.remote.api.AuthApi
import org.aystudios.skincare.data.repository.AuthRepositoryImpl
import org.aystudios.skincare.presentation.screens.auth.LoginSignUpScreenNavigator
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel
import org.aystudios.skincare.utils.TokenStorage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(settings: Settings) {

    val client = remember { createHttpClient() }
    val authApi = remember { AuthApi(client) }
    val tokenStorage = remember { TokenStorage(settings) }
    val authRepository = remember { AuthRepositoryImpl(authApi, tokenStorage, client) }
    val viewModel = remember { LoginViewModel(authRepository) }


    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

            Navigator(LoginSignUpScreenNavigator(viewModel, tokenStorage)) {
                SlideTransition(it)
            }
        }
    }
}