package org.aystudios.skincare.utils

import androidx.compose.runtime.staticCompositionLocalOf
import org.aystudios.skincare.domain.AuthRepository
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel

val LocalAuthRepository = staticCompositionLocalOf<AuthRepository> {
    error("AuthRepository not provided")
}

val LocalTokenStorage = staticCompositionLocalOf<TokenStorage> {
    error("TokenStorage not provided")
}

val LocalLoginViewModel = staticCompositionLocalOf<LoginViewModel> {
    error("LoginViewModel not provided")
}

val LocalProductViewModel = staticCompositionLocalOf<ProductViewModel> {
    error("ProductViewModel not provided")
}
