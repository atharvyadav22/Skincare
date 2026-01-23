package org.aystudios.skincare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import org.aystudios.skincare.core.network.providesAuthHttpClient
import org.aystudios.skincare.core.network.providesHttpFactory
import org.aystudios.skincare.data.remote.api.AuthApi
import org.aystudios.skincare.data.remote.api.ProductApi
import org.aystudios.skincare.data.repository.AuthRepositoryImpl
import org.aystudios.skincare.data.repository.ProductRepositoryImpl
import org.aystudios.skincare.presentation.screens.auth.LoginSignUpScreenNavigator
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel
import org.aystudios.skincare.utils.LocalLoginViewModel
import org.aystudios.skincare.utils.LocalProductViewModel
import org.aystudios.skincare.utils.LocalTokenStorage
import org.aystudios.skincare.utils.TokenRefresher
import org.aystudios.skincare.utils.TokenStorage

@Composable
fun App(settings: Settings) {

    val tokenStorage = remember { TokenStorage(settings) }
    val authClient = remember { providesAuthHttpClient() }
    val authApi = remember { AuthApi(authClient) }
    val tokenRefresher = remember { TokenRefresher(authApi, tokenStorage) }
    val client = remember { providesHttpFactory(tokenStorage, tokenRefresher) }
    val productApi = remember { ProductApi(client) }
    val authRepository = remember { AuthRepositoryImpl(authApi, tokenStorage, authClient) }

    val productRepository = remember { ProductRepositoryImpl(productApi, tokenStorage, client) }
    val loginViewModel = remember { LoginViewModel(authRepository) }
    val productViewModel = remember { ProductViewModel(productRepository) }

    CompositionLocalProvider(
        LocalTokenStorage provides tokenStorage,
        LocalLoginViewModel provides loginViewModel,
        LocalProductViewModel provides productViewModel
    ) {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Navigator(LoginSignUpScreenNavigator) {
                    SlideTransition(it)
                }
            }
        }
    }
}