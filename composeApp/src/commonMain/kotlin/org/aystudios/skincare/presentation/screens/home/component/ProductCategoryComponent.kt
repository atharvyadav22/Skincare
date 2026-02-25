package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.aystudios.skincare.presentation.components.ProductItemComponent
import org.aystudios.skincare.presentation.components.getAppRootNavigator
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel

@Composable
fun ProductCategoryComponent(category: String, productViewModel: ProductViewModel, favouritesViewModel: FavouritesViewModel) {

    val navigator = getAppRootNavigator()
    val listState = rememberLazyListState()
    val state by productViewModel.state(category).collectAsStateWithLifecycle()

    LaunchedEffect(state.items.isEmpty()) {
        productViewModel.load(category)
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        CategoriesLabelComponent(category) {
            navigator?.push(SeeAllProductsScreenNavigator(state, favouritesViewModel){ productViewModel.load(category)})
        }
        when {
            state.isLoading -> {
                Text("Loading...")
            }

            state.error != null -> {
                Text(state.error!!)
            }

            else -> {
                LazyRow(state = listState) {
                    items(state.items) {
                        ProductItemComponent(it, favouritesViewModel, false)
                    }
                }
            }

        }

    }


}