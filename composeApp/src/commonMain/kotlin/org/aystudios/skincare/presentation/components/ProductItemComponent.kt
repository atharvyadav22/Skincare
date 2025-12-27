package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.dummy

@Composable
fun ProductItemComponent() {

        Card(modifier = Modifier.padding(end = 8.dp, bottom = 8.dp).width(IntrinsicSize.Min)) {

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.background(AppSurfaceColor).padding(4.dp)
            ) {

                Box {
                    Image(
                        painter = painterResource(Res.drawable.dummy),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(1f).fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                    )

                    //Toggle Logic
                    var isFavourite by remember {
                        mutableStateOf(true)
                    }
                    FavouriteItemToggleComponent(
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                        isFavourite = isFavourite
                    ) { isFavourite = !isFavourite }

                }

                Column(modifier = Modifier.padding(start = 4.dp)) {
                    Text(
                        "Sunsscreen SPA 50+++",
                        modifier = Modifier.width(130.dp),
                        style = MaterialTheme.typography.titleMedium,
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
                                "₹799",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    textDecoration = TextDecoration.LineThrough,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = AppPrimaryColor
                            )
                            Text("₹599", style = MaterialTheme.typography.titleMedium)
                        }


                    }

                }
            }
        }
    }
