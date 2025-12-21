package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppButtonComponent(modifier: Modifier = Modifier, text: String) {

    Button(modifier = modifier.fillMaxWidth(), onClick = { } ) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}