package org.aystudios.skincare.presentation.screens.cart

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.data.remote.dto.CartItemResponseDTO
import org.aystudios.skincare.presentation.components.QtyChipButtonComponent
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppRedColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.cart
import skincare.composeapp.generated.resources.dummy

//TODO: Improve Logic
@Composable
fun SwipeToDeleteComponent(
    item: CartItemResponseDTO,
    onDelete: () -> Unit
) {

    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { totalDistance ->
            totalDistance * 0.85f
        }
    )

    LaunchedEffect(dismissState.targetValue) {
        if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
            onDelete()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            GmailDeleteBackground(dismissState)
        }
    ) {
        CartList(item)
    }
}

@Composable
fun GmailDeleteBackground(
    dismissState: SwipeToDismissBoxState
) {
    val progress = dismissState.progress

    val backgroundColor by animateColorAsState(
        targetValue = if (progress > 0.6f) AppRedColor else Color.Transparent,
        label = "bg"
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.CenterEnd
    ) {

        Icon(
            painter = painterResource(Res.drawable.cart),
            contentDescription = "Delete",
            tint = Color.White,
        )
    }

}

@Composable
private fun CartList(item: CartItemResponseDTO) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Image(
                painter = painterResource(Res.drawable.dummy),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.28f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                modifier = Modifier
                    .weight(0.72f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(
                        item.productName,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1
                    )

                    Text(
                        item.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "₹${item.originalPrice}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = AppPrimaryColor
                        )

                        Text(
                            "₹${item.discountPrice}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    var qty by remember(item.productId) {
                        mutableIntStateOf(item.quantity)
                    }

                    QtyChipButtonComponent(
                        count = qty,
                        onCountChange = { qty = it }
                    )
                }
            }
        }
    }
}
