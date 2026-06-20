package org.aystudios.skincare.presentation.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.presentation.components.AppButtonComponent
import org.aystudios.skincare.presentation.components.getAppRootNavigator
import org.aystudios.skincare.presentation.screens.checkout.CheckoutScreenNavigator
import org.aystudios.skincare.presentation.viewmodels.CartViewModel
import org.aystudios.skincare.ui.theme.AppBackgroundColor
import org.aystudios.skincare.ui.theme.AppScaffold
import org.koin.compose.koinInject

object CartScreenNavigator : Screen {
    @Composable
    override fun Content() {
        CartScreen()
    }
}

@Composable
fun CartScreen(viewModel: CartViewModel = koinInject()) {

    val navigator = getAppRootNavigator()
    val state by viewModel.cartState.collectAsStateWithLifecycle()

    AppScaffold(topBarTitle = "My Cart") {

        Column(
            modifier = it
                .padding(bottom = 52.dp)
                .background(AppBackgroundColor)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                when {
                    state.error != null -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Error: ${state.error}")
                        }
                    }

                    state.isLoading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Loading...")
                        }
                    }

                    state.cartItems.isEmpty() -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Cart is empty")
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                vertical = 12.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = state.cartItems,
                                key = { key -> key.productId }
                            ) { item ->

                                SwipeToDeleteComponent(item) {
                                    viewModel.deleteCartItem(item.productId)
                                }
                            }
                        }
                    }
                }
            }

            CartCheckoutFooter(
                totalPrice = state.totalPrice,
                isLoading = state.isLoading,
                error = state.error,
                onCheckout = { navigator?.push(CheckoutScreenNavigator(viewModel)) }
            )
        }
    }
}

@Composable
private fun CartCheckoutFooter(
    totalPrice: Double?,
    isLoading: Boolean,
    error: String?,
    onCheckout: () -> Unit
) {

    val totalText = when {
        error != null -> error
        isLoading -> "Loading..."
        else -> "₹$totalPrice"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppBackgroundColor)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Total Cost",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                totalText,
                style = MaterialTheme.typography.titleMedium
            )
        }

        AppButtonComponent(
            text = "Checkout",
            onClick = onCheckout,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
