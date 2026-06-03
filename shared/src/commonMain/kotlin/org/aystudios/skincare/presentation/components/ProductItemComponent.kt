package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.presentation.screens.details.DetailsScreenNavigator
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.shared.generated.resources.Res
import skincare.shared.generated.resources.dummy

@Composable
fun ProductItemComponent(
    item: ProductItemDTO,
    favouritesViewModel: FavouritesViewModel,
    isSeeAll: Boolean = true
) {
    val navigator = getAppRootNavigator()

    val padding = if(isSeeAll) PaddingValues(horizontal = 12.dp, vertical = 8.dp) else PaddingValues(end = 8.dp)

    Card(modifier = Modifier.padding(padding).width(IntrinsicSize.Min)) {

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.background(AppSurfaceColor).padding(4.dp).clickable {
                navigator?.push(DetailsScreenNavigator(item, favouritesViewModel))
            }
        ) {

            Box {
                Image(
                    painter = painterResource(Res.drawable.dummy),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.aspectRatio(1f).fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                )

                FavouriteItemToggleComponent(
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                    favouritesViewModel = favouritesViewModel,
                    product = item
                )

            }

            Column(modifier = Modifier.padding(start = 4.dp)) {
                Text(
                    item.name,
                    modifier = Modifier.width(136.dp),
                    style = MaterialTheme.typography.titleMedium,
                    minLines = 2,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier,
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
                        Text("₹${item.discountPrice}", style = MaterialTheme.typography.titleMedium)
                    }


                }

            }
        }
    }
}
