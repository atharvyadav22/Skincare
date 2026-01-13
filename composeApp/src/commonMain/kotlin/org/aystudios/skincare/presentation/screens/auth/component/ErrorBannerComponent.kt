package org.aystudios.skincare.presentation.screens.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppRedColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.toast_error

@Preview
@Composable
fun ErrorBannerComponent(message: String) {

    Row(Modifier.clip(RoundedCornerShape(8.dp)).background(AppRedColor).padding(horizontal = 2.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(Res.drawable.toast_error), contentDescription = null)
        Text(message, style = MaterialTheme.typography.bodyMedium)
    }
}