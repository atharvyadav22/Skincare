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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.presentation.components.BottomTabsTobBarComponent
import org.aystudios.skincare.presentation.screens.home.component.CategoriesComponent
import org.aystudios.skincare.presentation.screens.home.component.HorizontalCarouselComponent
import org.aystudios.skincare.presentation.screens.home.component.ProductCategoryComponent
import org.aystudios.skincare.presentation.screens.home.component.SearchBarComponent
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.utils.LocalProductViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

object HomeScreenNavigator : Screen {
    @Composable
    override fun Content() {
        HomeScreen()
    }

}

@Preview
@Composable
fun HomeScreen() {

    var searchText by rememberSaveable { mutableStateOf("") }
    val isSearchEmpty = searchText.isBlank()
    val productViewModel = LocalProductViewModel.current
    val allCategoriesState by productViewModel.allCategoryState.collectAsState()

    LaunchedEffect(Unit) {
        productViewModel.getAllCategories()
    }

    AppScaffold(showTopBar = false) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            BottomTabsTobBarComponent()

            SearchBarComponent(searchText) { searchText = it }

            AnimatedVisibility(
                visible = isSearchEmpty,
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
                    CategoriesComponent()

                    when (allCategoriesState) {
                        is ApiResult.Success -> {

                            (allCategoriesState as ApiResult.Success<List<String>>).data.forEach {
                                ProductCategoryComponent(it, productViewModel)

                            }
                        }

                        is ApiResult.Error -> {
                            Text((allCategoriesState as ApiResult.Error).message)
                        }

                        else -> Text("Loading... ")
                    }
                }
            }

            Spacer(modifier = Modifier.height(54.dp))
        }
    }
}

