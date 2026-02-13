package org.aystudios.skincare.presentation.screens.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.cross
import skincare.composeapp.generated.resources.search

@Composable
fun SearchBarComponent(value: String, onValueChange: (String) -> Unit){

    Row(modifier = Modifier.clip(CircleShape).fillMaxWidth().background(AppSurfaceColor).padding(horizontal = 12.dp,8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painter = painterResource(Res.drawable.search), contentDescription = null)
            BasicTextField(value = value, onValueChange =  {onValueChange(it)} , singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text))
        }

        if (value.isNotBlank()) Icon(painter =  painterResource(Res.drawable.cross), contentDescription = null, modifier = Modifier.clickable{onValueChange("")})

    }
}