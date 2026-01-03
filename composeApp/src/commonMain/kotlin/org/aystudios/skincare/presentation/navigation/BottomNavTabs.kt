package org.aystudios.skincare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.aystudios.skincare.presentation.screens.cart.CartScreenNavigator
import org.aystudios.skincare.presentation.screens.home.HomeScreenNavigator
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.cart
import skincare.composeapp.generated.resources.home
import skincare.composeapp.generated.resources.profile

object HomeTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val icon = painterResource(Res.drawable.home)

            return remember {
                TabOptions(0u, "Home", icon)
            }
        }

    @Composable
    override fun Content() {
        Navigator(HomeScreenNavigator)
    }

}

object CartTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val icon = painterResource(Res.drawable.cart)

            return remember {
                TabOptions(1u, "Cart", icon)
            }
        }

    @Composable
    override fun Content() {
        Navigator(CartScreenNavigator)
    }

}

object ProfileTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val icon = painterResource(Res.drawable.profile)

            return remember {
                TabOptions(2u, "Profile", icon)
            }
        }

    @Composable
    override fun Content() {
    }

}