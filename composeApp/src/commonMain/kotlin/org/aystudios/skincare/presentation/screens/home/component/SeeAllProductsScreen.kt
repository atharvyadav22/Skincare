package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.presentation.components.AppButtonComponent
import org.aystudios.skincare.presentation.components.ProductItemComponent
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.aystudios.skincare.presentation.viewmodels.ListPagingUIState
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppRedColor
import org.aystudios.skincare.ui.theme.AppScaffold

data class SeeAllProductsScreenNavigator(val state: ListPagingUIState<ProductItemDTO>, val favouritesViewModel: FavouritesViewModel, val isSearch: Boolean = false, val onLoadItems: () -> Unit) : Screen {
    @Composable
    override fun Content() {
        SeeAllProductsScreen(state, favouritesViewModel, isSearch) { onLoadItems() }
    }

}


@Composable
fun SeeAllProductsScreen(
    state: ListPagingUIState<ProductItemDTO>,
    favouritesViewModel: FavouritesViewModel,
    isSearch: Boolean,
    onLoadItems: () -> Unit
) {

    AppScaffold(isZeroPaddingValues = true, topBarTitle = "Products", showTopBar = !isSearch) {

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(150.dp),
        ) {

            // ----- ITEMS -----
            items(state.items) { item ->
                ProductItemComponent(item, favouritesViewModel)
            }

            // ----- LOADING -----
            if (state.isLoading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Loading...")
                    }
                }
            }

            // ----- ERROR -----
            state.error?.let { error ->
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = error,
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        AppButtonComponent(
                            modifier = Modifier.background(AppRedColor),
                            text = "Retry"
                        ) {
                            onLoadItems()
                        }
                    }
                }
            }

            // ----- LOAD MORE BUTTON -----
            if (!state.isLoading && !state.endReached && state.error == null) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AppButtonComponent(
                            modifier = Modifier.background(AppPrimaryColor),
                            text = "Load More"
                        ) {
                            onLoadItems()
                        }

                    }
                }
            }

            // ----- END REACHED -----
            if (state.endReached && state.items.isNotEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "You’ve reached the end")
                    }
                }
            }
        }
    }
}

/*
Auto Loading -
val listState = rememberLazyListState()

LaunchedEffect(listState) {
    snapshotFlow {
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
    }.collect { lastVisible ->
        if (lastVisible >= state.items.size - 4 &&
            !state.isLoading &&
            !state.endReached
        ) {
            viewModel.load(category)
        }
    }
}

*/
