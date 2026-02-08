package org.aystudios.skincare.di

import org.aystudios.skincare.core.network.providesAuthHttpClient
import org.aystudios.skincare.core.network.providesHttpClient
import org.aystudios.skincare.data.remote.api.AuthApi
import org.aystudios.skincare.data.remote.api.CartApi
import org.aystudios.skincare.data.remote.api.ProductApi
import org.aystudios.skincare.data.remote.api.UserApi
import org.aystudios.skincare.data.repository.AuthRepositoryImpl
import org.aystudios.skincare.data.repository.CartRepositoryImpl
import org.aystudios.skincare.data.repository.ProductRepositoryImpl
import org.aystudios.skincare.data.repository.UserRepositoryImpl
import org.aystudios.skincare.domain.AuthRepository
import org.aystudios.skincare.domain.CartRepository
import org.aystudios.skincare.domain.ProductRepository
import org.aystudios.skincare.domain.UserRepository
import org.aystudios.skincare.presentation.viewmodels.CartViewModel
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel
import org.aystudios.skincare.presentation.viewmodels.UserViewModel
import org.aystudios.skincare.utils.AppConfig
import org.aystudios.skincare.utils.TokenRefresher
import org.aystudios.skincare.utils.TokenStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

/*
    val tokenStorage = remember { TokenStorage(settings) }
    val authClient = remember { providesAuthHttpClient() }
    val baseUrl = remember(useLocal) {
            AppConfig.LOCAL_BASE_URL
}
    val authApi = remember(baseUrl) { AuthApi(authClient, baseUrl) }
    val tokenRefresher = remember { TokenRefresher(authApi, tokenStorage) }
    val client = remember { providesHttpFactory(tokenStorage, tokenRefresher) }
    val productApi = remember(baseUrl) { ProductApi(client, baseUrl) }
    val authRepository = remember { AuthRepositoryImpl(authApi, tokenStorage, authClient) }

    val productRepository = remember { ProductRepositoryImpl(productApi, tokenStorage, client) }
    val loginViewModel = remember { LoginViewModel(authRepository) }
    val productViewModel = remember { ProductViewModel(productRepository) }

    val userApi = remember(baseUrl) { UserApi(client, baseUrl) }
    val userRepository = remember{ UserRepositoryImpl(userApi) }
    val userViewModel = remember { UserViewModel(userRepository) }
 */

expect val platformSettingsModule: Module

val networkModule = module {

    single(named("AUTH_CLIENT")) {
        providesAuthHttpClient()
    }

    single(named("DEFAULT_CLIENT")) {
        providesHttpClient(get(), get())
    }

    single(named("BASE_URL")) {
        AppConfig.BASE_URL
    }

    single {
        AuthApi(get(named("AUTH_CLIENT")),get(named("BASE_URL")))
    }

    single {
        ProductApi(get(named("DEFAULT_CLIENT")),get(named("BASE_URL")))
    }
    single {
        UserApi(get(named("DEFAULT_CLIENT")), get(named("BASE_URL")))
    }
    single {
        CartApi(get(named("DEFAULT_CLIENT")), get(named("BASE_URL")))
    }

}

val dataModule = module {
    single { TokenStorage(get()) }
    single { TokenRefresher(get(), get()) }

    single { AuthRepositoryImpl(get(),get()) } bind AuthRepository::class
    single { ProductRepositoryImpl(get()) } bind ProductRepository::class
    single { UserRepositoryImpl(get()) } bind UserRepository::class
    single { CartRepositoryImpl(get()) } bind CartRepository::class
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { ProductViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { CartViewModel(get()) }
}