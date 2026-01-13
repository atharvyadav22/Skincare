package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.presentation.components.ProductItemComponent

@Composable
fun ProductCategoryComponent(title: String){

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        CategoriesLabelComponent(title) { }
        LazyRow {

            items(10){

                ProductItemComponent()

            }
        }
    }

}