package org.aystudios.skincare.presentation.screens.bottom_tabs_screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.ProductItemComponent
import org.aystudios.skincare.ui.theme.AppScaffold

object FavouriteScreenNavigator: Screen{
    @Composable
    override fun Content() {
        FavouriteScreen()
    }
}

@Composable
fun FavouriteScreen() {
    AppScaffold(enableManualScroll = false, topBarTitle = "My Favourite") {

        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {

            items(10){
                ProductItemComponent()
            }

            item {
                Spacer(modifier = Modifier.height(54.dp))
            }
        }
    }
}