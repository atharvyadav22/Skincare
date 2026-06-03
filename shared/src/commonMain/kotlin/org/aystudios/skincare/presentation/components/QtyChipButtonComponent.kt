package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppBackgroundColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skincare.shared.generated.resources.Res
import skincare.shared.generated.resources.add
import skincare.shared.generated.resources.subtract


@Composable
fun QtyChipButtonComponent(
    count: Int,
    onCountChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minCount: Int = 1,
    backgroundColor: Color = AppBackgroundColor,
    textColor: Color = Color.Black,
    iconSize: Dp = 20.dp,
    spacing: Dp = 8.dp
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically
    ) {

        QtyIconButton(
            iconSize = iconSize,
            background = Color.White,
            icon = Res.drawable.subtract,
            tint = Color.Black
        ) {
            if (count > minCount) {
                onCountChange(count - 1)
            }
        }

        Text(
            text = count.toString(),
            color = textColor
        )

        QtyIconButton(
            iconSize = iconSize,
            background = Color.White,
            icon = Res.drawable.add,
            tint = Color.Black
        ) {
            onCountChange(count + 1)
        }
    }
}
@Composable
private fun QtyIconButton(
    iconSize: Dp,
    background: Color,
    icon: DrawableResource,
    tint: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(iconSize)
        )
    }
}

