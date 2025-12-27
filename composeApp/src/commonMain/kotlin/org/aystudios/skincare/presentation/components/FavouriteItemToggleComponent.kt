package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppBackgroundColor
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.heart


@Composable
fun FavouriteItemToggleComponent(modifier: Modifier, isFavourite: Boolean, onClick: () -> Unit){

    val tint = if (isFavourite) AppPrimaryColor else AppBackgroundColor


    Box(modifier = modifier.size(28.dp).clip(CircleShape).background(AppSurfaceColor).clickable { onClick() }){
        Icon(painter = painterResource(Res.drawable.heart), contentDescription = null, tint = tint, modifier = Modifier.padding(4.dp).align(Alignment.Center))
    }
}