package org.aystudios.skincare.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.aystudios.skincare.utils.LocalBottomBarProgress

object AppBottomNavigator : Screen {

    @Composable
    override fun Content() {
        AppBottomNavigation()
    }

}


@Composable
fun AppBottomNavigation(tab: Tab = HomeTab) {

    val bottomBarProgress = remember { mutableStateOf(0f) }

    TabNavigator(tab) {

        CompositionLocalProvider(
            LocalBottomBarProgress provides bottomBarProgress
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                CurrentTab()

                val barHeight = 80.dp

                val offsetY by animateDpAsState(
                    targetValue = barHeight * bottomBarProgress.value,
                    label = "bottomOffset"
                )

                AppCustomBottomBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = offsetY)
                )

            }
        }
    }
}

@Composable
fun AppCustomBottomBar(
    modifier: Modifier = Modifier
) {
    val tabNavigator = LocalTabNavigator.current
    val tabs = listOf(HomeTab, CartTab, ProfileTab)

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(AppSurfaceColor)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(
            12.dp,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach { tab ->
            CustomTabItem(
                tab = tab,
                selected = tabNavigator.current == tab
            ) { tabNavigator.current = tab }
        }
    }
}

@Composable
fun CustomTabItem(
    tab: Tab,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) AppPrimaryColor else AppSurfaceColor,
        animationSpec = tween(durationMillis = 300),
        label = "background"
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) Color.White else Color.Gray,
        animationSpec = tween(durationMillis = 300),
        label = "content"
    )

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }) { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        tab.options.icon?.let {
            Icon(
                painter = it,
                contentDescription = tab.options.title,
                tint = contentColor,
                modifier = Modifier.size(28.dp)
            )
        }

        if (selected) Spacer(modifier = Modifier.width(8.dp))

        AnimatedVisibility(
            visible = selected,
        ) {
            Text(
                text = tab.options.title,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

