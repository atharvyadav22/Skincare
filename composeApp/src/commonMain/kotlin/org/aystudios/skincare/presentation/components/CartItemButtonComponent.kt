package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppBackgroundColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.add
import skincare.composeapp.generated.resources.subtract


@Preview
@Composable
fun cartItemButtonComponent(): Int {

    var count by remember { mutableIntStateOf(1) }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .clip(CircleShape)
        .background(
            AppBackgroundColor
        )
        .padding(4.dp)) {

        CircularButton(Color.White, Res.drawable.subtract, Color.Black){
            if(count > 1) count--
        }
        Text("$count")
        CircularButton(Color.Black, Res.drawable.add, Color.White){
            count++
        }

    }

    return count
}

@Composable
private fun CircularButton(background: Color, drawable: DrawableResource, tint: Color, onClick: () -> Unit){

    Box(modifier = Modifier
        .size(20.dp)
        .clip(CircleShape)
        .background(background)
        .clickable{ onClick() }){
        Icon(painter = painterResource(drawable), contentDescription = null, tint = tint)
    }
}