package org.aystudios.skincare.presentation.screens.bottom_tabs_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.AppButtonComponent
import org.aystudios.skincare.presentation.components.cartItemButtonComponent
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppScaffold
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.dummy

object CartScreenNavigator : Screen {
    @Composable
    override fun Content() {
        CartScreen()
    }
}

@Preview
@Composable
fun CartScreen() {

    AppScaffold(enableManualScroll = false, topBarTitle = "My Cart") {

        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) {
                    CartList()
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            val total = 0.00

            Column(
                modifier = Modifier.padding(top = 4.dp, bottom = 54.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Cost", style = MaterialTheme.typography.titleMedium)
                    Text("₹total", style = MaterialTheme.typography.titleMedium)
                }
                AppButtonComponent(text = "Checkout")
            }


        }


    }
}

@Composable
private fun CartList() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Square image
            Image(
                painter = painterResource(Res.drawable.dummy),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f) // square
                    .fillMaxHeight() // ✅ image height = row height
                    .clip(RoundedCornerShape(12.dp))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text("Gentle Cleanser", style = MaterialTheme.typography.titleMedium, maxLines = 1)
                Text(
                    "Soft-foaming cleanser that gently purifies and hydrates for a radiant glow.",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
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

                    val count = cartItemButtonComponent()
                }
            }
        }
    }
}
