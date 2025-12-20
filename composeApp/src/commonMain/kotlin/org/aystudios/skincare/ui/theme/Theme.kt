package org.aystudios.skincare.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.getAppNavigator
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.back

@Composable
fun CircularBackButton() {

    val navigator = getAppNavigator()

    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(AppSurfaceColor)
            .clickable { navigator.pop() },
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(Res.drawable.back),
            contentDescription = null,
            tint = AppOnBackgroundColor,
        )
    }
}

@Composable
fun AppTopBar(title: String) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {

        CircularBackButton()


        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W400),
            textAlign = TextAlign.Center,
            text = title
        )

    }
}

@Composable
fun AppScaffold(
    isZeroPaddingValues: Boolean = false,
    enableManualScroll: Boolean = true,
    showTopBar: Boolean = true,
    topBarTitle: String = "",
    content: @Composable () -> Unit
) {
    val paddingValues = if (isZeroPaddingValues) {
        PaddingValues(0.dp)
    } else {
        PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    }

    AppMaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackgroundColor)
                .padding(paddingValues)
        ) {
            if (showTopBar) {
                AppTopBar(title = topBarTitle)
            }

            val bodyModifier = Modifier.weight(1f).let {
                if (enableManualScroll) it.verticalScroll(rememberScrollState())
                else it
            }

            Box(modifier = bodyModifier) {
                    content()
            }


        }
    }
}

@Composable
fun AppMaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = AppColorScheme) {

        content()

    }
}

