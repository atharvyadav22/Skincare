package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.presentation.components.getAppRootNavigator
import org.aystudios.skincare.presentation.screens.favourite.FavouriteScreenNavigator
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.aystudios.skincare.ui.theme.CircularButtonComponent
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.app_logo
import skincare.composeapp.generated.resources.favourite

@Composable
fun HomeScreenTopBarComponent() {

    val navigator = getAppRootNavigator()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            ProfileComponent()
            Image(
                painter = painterResource(Res.drawable.app_logo),
                modifier = Modifier.height(38.dp),
                contentScale = ContentScale.Fit,
                contentDescription = "App Logo"
            )

        }

        CircularButtonComponent(painter = Res.drawable.favourite) {
            navigator?.push(FavouriteScreenNavigator)
        }

    }
}

@Composable
private fun ProfileComponent() {
    Image(
        painter = ColorPainter(Color.Black),
        modifier = Modifier.size(38.dp).clip(CircleShape),
        contentDescription = null
    )
}