package org.aystudios.skincare.presentation.screens.favourite

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.ProductItemComponent
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.aystudios.skincare.ui.theme.AppScaffold

data class FavouriteScreenNavigator(val viewModel: FavouritesViewModel) : Screen {
    @Composable
    override fun Content() {
        FavouriteScreen(viewModel)
    }
}

@Composable
fun FavouriteScreen(viewModel: FavouritesViewModel) {

    val state by viewModel.favourites.collectAsStateWithLifecycle()

    AppScaffold(topBarTitle = "My Favourite") {

        when {
            state.isLoading -> {
                Text("Loading...")
            }

            state.error != null -> {
                Text("${state.error}")
            }

            else -> {
                LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
                    items(state.items, key = { it.id }) {
                        ProductItemComponent(it, viewModel)
                    }
                }
            }
        }
    }
}