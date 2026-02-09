package org.aystudios.skincare.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.presentation.components.getAppRootNavigator
import org.aystudios.skincare.presentation.screens.auth.LoginSignUpScreenNavigator
import org.aystudios.skincare.presentation.screens.profile.component.ProfileTabCategorySectionComponent
import org.aystudios.skincare.presentation.screens.profile.component.ProfileTabProfileComponent
import org.aystudios.skincare.presentation.screens.profile.component.ProfileTabSectionComponent
import org.aystudios.skincare.presentation.viewmodels.UserViewModel
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.aystudios.skincare.utils.LocalTokenStorage
import org.aystudios.skincare.utils.LocalUserViewModel
import org.aystudios.skincare.utils.TokenStorage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.profile_tab_change_password
import skincare.composeapp.generated.resources.profile_tab_location
import skincare.composeapp.generated.resources.profile_tab_logout
import skincare.composeapp.generated.resources.profile_tab_my_orders
import skincare.composeapp.generated.resources.profile_tab_profile
import skincare.composeapp.generated.resources.profile_tab_track_orders

object ProfileScreenNavigator : Screen {

    @Composable
    override fun Content() {
        ProfileScreen()
    }

}

@Composable
fun ProfileScreen(viewModel: UserViewModel = koinViewModel()) {
    val navigator = getAppRootNavigator()
    val tokenStorage: TokenStorage = koinInject<TokenStorage>()

    AppScaffold(showTopBar = false) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ProfileTabProfileComponent(viewModel)

            ProfileTabSectionComponent("Account") {
                ProfileTabCategorySectionComponent(
                    Res.drawable.profile_tab_profile,
                    "Your Profile"
                ) { navigator?.push(EditProfileScreenNavigator(viewModel)) }

                ProfileTabCategorySectionComponent(
                    Res.drawable.profile_tab_location,
                    "Manage Address"
                ) {}

                ProfileTabCategorySectionComponent(
                    Res.drawable.profile_tab_change_password,
                    "Change Password"
                ) {}

            }

            ProfileTabSectionComponent("Orders") {
                ProfileTabCategorySectionComponent(
                    Res.drawable.profile_tab_my_orders,
                    "My Orders"
                ) {}
                ProfileTabCategorySectionComponent(
                    Res.drawable.profile_tab_track_orders,
                    "Track Orders"
                ) {}

            }

            val navigator = getAppRootNavigator()
            Card(colors = CardDefaults.cardColors(AppSurfaceColor)) {

                ProfileTabCategorySectionComponent(
                    icon = Res.drawable.profile_tab_logout,
                    "Logout",
                    isLogout = true
                ) {
                    tokenStorage.clear()
                    navigator?.replaceAll(LoginSignUpScreenNavigator)

                }
            }

            Spacer(Modifier.height(56.dp))


        }

    }
}



