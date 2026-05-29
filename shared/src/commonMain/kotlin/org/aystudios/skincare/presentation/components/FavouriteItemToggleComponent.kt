package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.aystudios.skincare.ui.theme.AppBackgroundColor
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.shared.generated.resources.heart
import skincare.shared.generated.resources.Res


@Composable
fun FavouriteItemToggleComponent(modifier: Modifier = Modifier, favouritesViewModel: FavouritesViewModel, product: ProductItemDTO, iconSize: Dp = 28.dp){

    val state by favouritesViewModel.favourites.collectAsStateWithLifecycle()

    val isFavourite = state.items.any { it.id == product.id }

    val tint = if (isFavourite) AppPrimaryColor else AppBackgroundColor

    Box(modifier = modifier.clip(CircleShape).background(AppSurfaceColor).clickable { favouritesViewModel.toggleFavourite(product) }){
        Icon(painter = painterResource(Res.drawable.heart), contentDescription = null, tint = tint, modifier = Modifier.size(iconSize).padding(4.dp).align(Alignment.Center))
    }
}