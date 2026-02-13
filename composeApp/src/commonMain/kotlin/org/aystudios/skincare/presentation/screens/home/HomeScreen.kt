package org.aystudios.skincare.presentation.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.ProductItemComponent
import org.aystudios.skincare.presentation.screens.home.component.CategoriesComponent
import org.aystudios.skincare.presentation.screens.home.component.HomeScreenTopBarComponent
import org.aystudios.skincare.presentation.screens.home.component.HorizontalCarouselComponent
import org.aystudios.skincare.presentation.screens.home.component.ProductCategoryComponent
import org.aystudios.skincare.presentation.screens.home.component.SearchBarComponent
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.utils.LocalBottomBarProgress
import org.koin.compose.viewmodel.koinViewModel

object HomeScreenNavigator : Screen {
    @Composable
    override fun Content() {
        HomeScreen()
    }

}

@Composable
fun HomeScreen() {

    var searchText by rememberSaveable { mutableStateOf("") }
    val isSearching = searchText.isNotBlank()

    val productViewModel: ProductViewModel = koinViewModel()

    val categoriesState by productViewModel
        .allCategoryState
        .collectAsStateWithLifecycle()

    val searchQuery by productViewModel.searchQuery.collectAsStateWithLifecycle()
    val searchState by productViewModel.searchState.collectAsStateWithLifecycle()



    AppScaffold(showTopBar = false, enableScroll = !isSearching) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            HomeScreenTopBarComponent()

            SearchBarComponent(searchText) {
                searchText = it
                productViewModel.onSearchQueryChange(it)
            }

            if(searchQuery.isNotBlank()){
//                SeeAllProductsScreen(state = searchState, isSearch = true) {}

                val progressState = LocalBottomBarProgress.current
                val gridState = rememberLazyGridState()

                LaunchedEffect(gridState) {

                    snapshotFlow {
                        gridState.firstVisibleItemScrollOffset
                    }.collect { offset ->

                        // 🔥 Scroll range define karo
                        val maxScroll = 200f

                        val progress = (offset / maxScroll)
                            .coerceIn(0f, 1f)

                        progressState.value = progress
                    }
                }


                when{
                    searchState.isLoading -> {Text("Loading...")}
                    searchState.error != null -> {Text(searchState.error ?: "Error")}
                    searchState.items.isEmpty() -> {Text("No Products Found")}
                    else -> {


                        LazyVerticalGrid(
                            state = gridState,
                            modifier = Modifier.fillMaxSize(),
                            columns = GridCells.Adaptive(150.dp),
                        ) {
                            items(searchState.items){
                                ProductItemComponent(it)
                            }
                        }

                    }
                }
            }
            else {
                AnimatedVisibility(
                    visible = !isSearching,
                    enter = fadeIn(animationSpec = tween(250)) + expandVertically(
                        animationSpec = tween(
                            300
                        )
                    ),
                    exit = fadeOut(animationSpec = tween(200)) + shrinkVertically(
                        animationSpec = tween(
                            220
                        )
                    )
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        HorizontalCarouselComponent()

                        when{
                            categoriesState.isLoading -> {
                                Text("Loading...")
                            }
                            categoriesState.error != null -> {
                                Text(categoriesState.error ?: "Error")
                            }
                            else -> {
                                CategoriesComponent(categoriesState.items)
                            }
                        }

                        when {
                            categoriesState.isLoading -> Text("Loading...")
                            categoriesState.error != null ->
                                Text(categoriesState.error ?: "Error")

                            else -> {
                                listOf(
                                    "Facewash",
                                    "Moisturizer",
                                    "Sunscreen",
                                    "Serum"
                                ).forEach {
                                    ProductCategoryComponent(it, productViewModel)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(54.dp))
                }
            }
        }
    }
}

