package org.aystudios.skincare.presentation.screens.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.forward
import kotlin.math.log

@Composable
fun ProfileTabSectionComponent(title: String, content: @Composable ColumnScope.() -> Unit){

    Card(colors = CardDefaults.cardColors(AppSurfaceColor), modifier = Modifier.fillMaxWidth()) {

        Column(Modifier.padding(top = 24.dp, bottom = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(12.dp)){
                Box(modifier = Modifier.fillMaxHeight().width(4.dp).clip(RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100)).background(AppPrimaryColor))
                Text(title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W400))
            }
            Column(content =  content)

        }

    }
}

@Composable
fun ProfileTabCategorySectionComponent(icon: DrawableResource, title: String,isLogout: Boolean = false, onClick: () -> Unit ){

    val logoutColor = if(isLogout) AppPrimaryColor else Color.Black
    Row(modifier = Modifier.fillMaxWidth().clickable{ onClick() }.padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(icon), modifier = Modifier.size(24.dp), contentDescription = null, tint = logoutColor)
            Text(title,style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W400), color = logoutColor)
        }

        Icon(painter = painterResource(Res.drawable.forward), contentDescription = null, tint = if (isLogout) AppPrimaryColor else Color.DarkGray)

    }
}