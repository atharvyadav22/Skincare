package org.aystudios.skincare.presentation.screens.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.data.remote.dto.CartItemResponseDTO
import org.aystudios.skincare.data.remote.dto.PaymentMode
import org.aystudios.skincare.data.remote.dto.UserProfileResponseDTO
import org.aystudios.skincare.presentation.components.AppButtonComponent
import org.aystudios.skincare.presentation.components.getAppRootNavigator
import org.aystudios.skincare.presentation.screens.payment.OrderProcessingScreenNavigator
import org.aystudios.skincare.presentation.viewmodels.CartViewModel
import org.aystudios.skincare.ui.theme.AppGreenColor
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.aystudios.skincare.utils.AppLogger
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.check

class CheckoutScreenNavigator(val viewModel: CartViewModel) : Screen {
    @Composable
    override fun Content() {
        CheckoutScreen(viewModel)
    }

}

//TODO: Implement this pattern to other screens
@Composable
fun CheckoutScreen(cartViewModel: CartViewModel) {

    val checkoutState by cartViewModel.checkout.collectAsStateWithLifecycle()


    val navigator = getAppRootNavigator()

    LaunchedEffect(Unit) {
        cartViewModel.checkout()
    }

    AppScaffold(topBarTitle = "Order Summary") { modifier ->
        Box(modifier = modifier) {

            checkoutState.cartItems?.let { data ->

                var selected by remember { mutableStateOf(data.paymentModes.first()) }

                CheckoutContent(
                    items = data.cart.cartItems,
                    totalPrice = data.cart.totalPrice,
                    deliveryFee = 50.0,
                    availableOption = data.paymentModes,
                    selected = selected,
                    paymentMethodSelected = { selected = it},
                    userDetails = data.userProfile,
                    onPayClick = {
                        navigator?.push(OrderProcessingScreenNavigator(selected))
                    }
                )
            }

            if (checkoutState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (checkoutState.error != null) {
                Text(text = checkoutState.error!!, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun CheckoutContent(
    modifier: Modifier = Modifier,
    items: List<CartItemResponseDTO>,
    totalPrice: Double,
    deliveryFee: Double,
    availableOption: List<PaymentMode>,
    selected: PaymentMode,
    paymentMethodSelected: (PaymentMode) -> Unit,
    userDetails: UserProfileResponseDTO,
    onPayClick: () -> Unit
) {

    Column(modifier = modifier) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TitleComponent("Cart Items") {
                CartSummaryComponent(items)
                Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                    PriceComponent("Delivery Charge", deliveryFee)
                    PriceComponent("Total", totalPrice)
                }
            }
            TitleComponent("Payment Options") {
                PaymentModeSection(
                    availableOption = availableOption,
                    selected = selected,
                    paymentMethodSelected = paymentMethodSelected
                )
            }

            TitleComponent("Shipping Details") {
                ShippingDetailsComponent(userDetails)
            }
        }
        AppButtonComponent(text = "Pay ₹$totalPrice", onClick = onPayClick)
    }
}

@Composable
private fun CartSummaryComponent(items: List<CartItemResponseDTO>) {

    Box(
        modifier = Modifier
            .dashBorder(Color.DarkGray)
            .padding(12.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items.forEach {
                Column {

                    Text(
                        it.productName,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        it.brand,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.DarkGray
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Qty ${it.quantity}", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            "₹${it.discountPrice}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                if (items.indexOf(it) < items.lastIndex) {
                    HorizontalDivider(color = Color.Gray.copy(alpha = 0.4f))
                }

            }

        }

    }
}


private fun Modifier.dashBorder(
    color: Color,
    strokeWidth: Dp = 1.dp,
    gapLength: Dp = 4.dp,
    dashLength: Dp = 12.dp,
    cornerRadius: Dp = 12.dp
) = this.drawBehind {

    val stroke = strokeWidth.toPx()
    val dash = dashLength.toPx()
    val gap = gapLength.toPx()
    val radius = cornerRadius.toPx()

    drawRoundRect(
        color = color,
        style = Stroke(
            width = stroke,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dash, gap), 0f
            )
        ),
        cornerRadius = CornerRadius(radius)
    )
}

@Composable
private fun TitleComponent(text: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxHeight().width(4.dp).clip(
                    RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100)
                ).background(AppPrimaryColor)
            )
            Text(
                text,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500)
            )

        }
        content()
    }
}

@Composable
private fun PriceComponent(priceType: String, price: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$priceType:",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W500)
        )
        Text(
            "₹${price}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}


@Composable
fun PaymentModeSection(
    availableOption: List<PaymentMode>,
    selected: PaymentMode,
    paymentMethodSelected: (PaymentMode) -> Unit
) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        availableOption.forEach { mode ->

            val isSelected = mode == selected

            Card(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { paymentMethodSelected(mode) },
                colors = CardDefaults.cardColors(
                    if (isSelected) AppSurfaceColor
                    else AppSurfaceColor.copy(alpha = 0.6f)
                ),
                border = if (isSelected)
                    BorderStroke(1.5.dp, AppPrimaryColor)
                else
                    BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f))
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = mode.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (isSelected) {
                        Icon(
                            painter = painterResource(Res.drawable.check),
                            contentDescription = null,
                            tint = AppGreenColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShippingDetailsComponent(userDetails: UserProfileResponseDTO) {

    Card(
        colors = CardDefaults.cardColors(AppSurfaceColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {

            Text(
                userDetails.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                userDetails.phoneNo,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                userDetails.address,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
