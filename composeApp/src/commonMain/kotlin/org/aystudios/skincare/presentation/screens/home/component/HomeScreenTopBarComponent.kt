package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.location

@Composable
fun HomeScreenTopBarComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .clip(CircleShape)
                .background(AppSurfaceColor)
                .padding(8.dp)) {
                Icon(
                    painter = painterResource(Res.drawable.location),
                    tint = AppPrimaryColor,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text("Home", style = MaterialTheme.typography.labelLarge)
                Text(
                    "Shani Mandi, Pimple Gurav",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
                    modifier = Modifier.width(150.dp),
                    maxLines = 1
                )
            }
        }

        Icon(
            painter = ColorPainter(Color.Gray),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(38.dp)
        )
    }
}