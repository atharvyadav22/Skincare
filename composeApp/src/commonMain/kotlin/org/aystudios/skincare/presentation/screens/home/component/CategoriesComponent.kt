package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.aystudios.skincare.presentation.components.getAppRootNavigator
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.aystudios.skincare.presentation.viewmodels.ListPagingUIState
import org.aystudios.skincare.presentation.viewmodels.ProductViewModel
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.cleanser

@Composable
fun CategoriesComponent(items: List<String>, favouritesViewModel: FavouritesViewModel, productViewModel: ProductViewModel) {

    val navigator = getAppRootNavigator()


    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        CategoriesLabelComponent("Categories") { }

        LazyRow {
            items(items) {
                val state by productViewModel.state(it).collectAsStateWithLifecycle()

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 8.dp).clickable{
                        navigator?.push(SeeAllProductsScreenNavigator(state, favouritesViewModel){ productViewModel.load(it)})
                    }
                ) {
                    Image(
                        painter = painterResource(Res.drawable.cleanser),
                        modifier = Modifier.size(96.dp).clip(
                            RoundedCornerShape(12.dp)
                        ),
                        contentDescription = null
                    )
                    Text(it, style = MaterialTheme.typography.labelLarge)
                }
            }
        }

    }
}
