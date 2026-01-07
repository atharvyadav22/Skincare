package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.presentation.screens.cart.CartScreenNavigator
import org.aystudios.skincare.presentation.screens.favourite.FavouriteScreenNavigator
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.app_logo
import skincare.composeapp.generated.resources.cart
import skincare.composeapp.generated.resources.favourite
import skincare.composeapp.generated.resources.location

@Composable
fun BottomTabsTobBarComponent(isHomeScreen: Boolean = true) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            if (isHomeScreen) {
                ProfileComponent()
                Image(painter = painterResource(Res.drawable.app_logo),
                    modifier = Modifier.height(38.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = "App Logo")

            }
//            else {
//                Box(
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .background(AppSurfaceColor)
//                        .padding(8.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(Res.drawable.location),
//                        tint = AppPrimaryColor,
//                        contentDescription = null
//                    )
//                }
//                Spacer(modifier = Modifier.width(8.dp))
//                Column {
//                    Text("Home", style = MaterialTheme.typography.labelLarge)
//                    Text(
//                        "Shani Mandi, Pimple Gurav",
//                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
//                        modifier = Modifier.width(150.dp),
//                        maxLines = 1
//                    )
//                }
//            }
        }

        CircularIconComponent(painter = painterResource(Res.drawable.favourite), AppPrimaryColor)


    }
}

@Composable
private fun CircularIconComponent(painter: Painter, tint: Color) {
    val navigator = getAppRootNavigator()
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(AppSurfaceColor)
            .clickable{ navigator?.push(FavouriteScreenNavigator)}
            .padding(8.dp)
    ) {
        Icon(
            painter = painter,
            tint = tint,
            contentDescription = null
        )
    }
}

@Composable
private fun ProfileComponent(){
    Image(painter = ColorPainter(Color.Black),
        modifier = Modifier.size(38.dp).clip(CircleShape),
        contentDescription = null)
}