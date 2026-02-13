package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.visibility_off
import skincare.composeapp.generated.resources.visibility_on

@Composable
fun InputTextComponent(
    title: String,
    label: String,
    text: String,
    keyboardType: KeyboardType,
    enabled: Boolean = true,
    isPassword: Boolean = false,
    onValueChanged: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Row(modifier = Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.fillMaxHeight().width(4.dp).clip(RoundedCornerShape(topEndPercent = 100, bottomEndPercent = 100)).background(AppPrimaryColor))
            Text(title, style = MaterialTheme.typography.titleMedium)
        }

        OutlinedTextField(
            value = text,
            onValueChange = { onValueChanged(it) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            enabled = enabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                if (isPassword) {
                    val image =
                        if (passwordVisible) Res.drawable.visibility_on else Res.drawable.visibility_off
                    Icon(
                        painter = painterResource(image),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                }
            }
        )
    }
}