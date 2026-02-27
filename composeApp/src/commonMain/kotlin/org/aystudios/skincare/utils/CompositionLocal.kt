package org.aystudios.skincare.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import org.aystudios.skincare.domain.AuthRepository
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel
import org.aystudios.skincare.presentation.viewmodels.UserViewModel

val LocalBottomBarProgress =
    compositionLocalOf<MutableState<Float>> {
        error("Not provided")
    }
