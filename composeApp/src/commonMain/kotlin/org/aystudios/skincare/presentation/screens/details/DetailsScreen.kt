package org.aystudios.skincare.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.data.remote.dto.CartRequestDTO
import org.aystudios.skincare.data.remote.dto.ProductItemDTO
import org.aystudios.skincare.presentation.components.AppButtonComponent
import org.aystudios.skincare.presentation.components.FavouriteItemToggleComponent
import org.aystudios.skincare.presentation.components.QtyChipButtonComponent
import org.aystudios.skincare.presentation.screens.details.components.DetailsLabelComponent
import org.aystudios.skincare.presentation.screens.details.components.DetailsTopAppBarComponent
import org.aystudios.skincare.presentation.screens.details.components.PriceAndReviewComponent
import org.aystudios.skincare.presentation.viewmodels.CartViewModel
import org.aystudios.skincare.presentation.viewmodels.FavouritesViewModel
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.cleanser

data class DetailsScreenNavigator(
    val item: ProductItemDTO,
    val favouritesViewModel: FavouritesViewModel
) : Screen {
    @Composable
    override fun Content() {
        DetailsScreen(item, favouritesViewModel)
    }


    @Composable
    fun DetailsScreen(
        item: ProductItemDTO,
        favouritesViewModel: FavouritesViewModel,
        cartViewModel: CartViewModel = koinInject()
    ) {

        val cartState by cartViewModel.cartState.collectAsStateWithLifecycle()
        val scrollState = rememberScrollState()
        var qty by remember { mutableIntStateOf(1) }

        AppScaffold(showTopBar = false, isZeroPaddingValues = true) {
            Column {
                DetailsTopAppBarComponent()
                Card(
                    colors = CardDefaults.cardColors(AppSurfaceColor),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.verticalScroll(scrollState).padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            //Heading Image
                            HeadingImageComponent(item, favouritesViewModel)


                            //Labels
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                DetailsLabelComponent(item.category)
                                DetailsLabelComponent(item.brand)
                            }


                            //Product Title
                            Text(
                                item.name,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.W500)
                            )

                            //Price & Review
                            PriceAndReviewComponent(item.discountPrice, item.originalPrice)


                            //Description
                            Column {
                                Text("Description", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    item.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            //Spacer
                            Spacer(Modifier.height(56.dp))

                        }

                        Row(
                            modifier = Modifier.padding(16.dp).align(Alignment.BottomCenter),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AppButtonComponent(
                                modifier = Modifier.weight(1f),
                                "Add To Cart",
                                enabled = !cartState.isLoading
                            ) {
                                val dto = CartRequestDTO(item.id, qty)
                                cartViewModel.addToCart(dto)

                            }

                            QtyChipButtonComponent(
                                count = qty,
                                onCountChange = { qty = it },
                                modifier = Modifier.padding(8.dp),
                                iconSize = 28.dp
                            )
                        }
                    }

                }


            }
        }
    }

    @Composable
    private fun HeadingImageComponent(product: ProductItemDTO, favouritesViewModel: FavouritesViewModel) {

        Box {
            Image(
                painter = painterResource(Res.drawable.cleanser),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(4 / 3f)
                    .clip(RoundedCornerShape(32.dp))
            )

            FavouriteItemToggleComponent(
                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                favouritesViewModel = favouritesViewModel,
                product = product,
                iconSize = 36.dp
            )
        }

    }

}
