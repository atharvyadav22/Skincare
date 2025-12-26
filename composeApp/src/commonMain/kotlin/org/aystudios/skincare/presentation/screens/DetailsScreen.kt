package org.aystudios.skincare.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.AppButtonComponent
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.ui.theme.CircularBackButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.cart
import skincare.composeapp.generated.resources.cleanser

object DetailsScreenNavigator : Screen {
    @Composable
    override fun Content() {
        DetailsScreen()
    }

}

@Preview
@Composable
fun DetailsScreen() {

    AppScaffold(showTopBar = false, isZeroPaddingValues = true, enableManualScroll = false) {

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                DetailsTopAppBarComponent()

                Card(
                    colors = CardDefaults.cardColors(Color.White),
                    shape = RoundedCornerShape(topStartPercent = 15, topEndPercent = 15)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        //Heading Image
                        Image(
                            painter = painterResource(Res.drawable.cleanser),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().aspectRatio(4 / 3f)
                                .clip(RoundedCornerShape(topStartPercent = 15, topEndPercent = 15))
                        )

                        //Product Title
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Gentle Hydrating Cleanser",
                                modifier = Modifier.weight(1f),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W500)
                            )
                            CircularBackButton()
                        }

                        //Price & Review
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {

                                Text("From: ", style = MaterialTheme.typography.titleMedium)

                                Text(
                                    "₹599", style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.W500
                                    )
                                )

                                Text(
                                    "₹799",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        textDecoration = TextDecoration.LineThrough,
                                        fontWeight = FontWeight.W500
                                    ),
                                    color = AppPrimaryColor
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("⭐", style = MaterialTheme.typography.titleMedium)
                                Text("4.7/5", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "(2k+ Review)",
                                    style = MaterialTheme.typography.titleSmall.copy(color = Color.LightGray)
                                )

                            }
                        }

                        //Description
                        Column() {
                            Text("Description", style = MaterialTheme.typography.titleMedium)
                            Text(
                                "Glow’s Gentle Foaming Cleanser lathers into a soft, bubbly foam that gently removes impurities and makeup while soothing and hydrating skin for a refreshed, radiant glow.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }


                    }
                }
            }

            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppButtonComponent(modifier = Modifier.weight(1f),"Add To Cart")
                CircularBackButton()
            }

        }
    }
}

@Composable
private fun DetailsTopAppBarComponent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CircularBackButton()

        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W400),
            textAlign = TextAlign.Center,
            text = "Details"
        )

        CircularBackButton(Res.drawable.cart)
    }
}