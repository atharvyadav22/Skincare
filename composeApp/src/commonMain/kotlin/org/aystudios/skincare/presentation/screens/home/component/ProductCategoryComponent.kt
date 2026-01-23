package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.presentation.components.ProductItemComponent
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel

@Composable
fun ProductCategoryComponent(title: String, productViewModel: ProductViewModel) {
    val state by productViewModel.state(title).collectAsState()

    LaunchedEffect(title) {
        productViewModel.loadFirstPage(title)
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        CategoriesLabelComponent(title) { }
        when {
            state.isInitialLoading -> {
                Text("Loading...")
            }

            state.error != null -> {
                Text(state.error!!)
            }

            else -> {
                LazyRow {

                    items(state.items) {
                        ProductItemComponent(it)

                    }
                }
            }
        }

    }

}