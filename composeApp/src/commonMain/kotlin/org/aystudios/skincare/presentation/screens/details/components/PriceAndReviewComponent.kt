package org.aystudios.skincare.presentation.screens.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppPrimaryColor


@Composable
fun PriceAndReviewComponent(discountPrice: Double, originalPrice: Double) {
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
                "₹${discountPrice}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W500
                )
            )

            Text(
                "₹${originalPrice}",
                style = MaterialTheme.typography.bodyMedium.copy(
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
}