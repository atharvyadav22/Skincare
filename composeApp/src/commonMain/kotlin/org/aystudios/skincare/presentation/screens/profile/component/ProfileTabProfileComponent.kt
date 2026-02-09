package org.aystudios.skincare.presentation.screens.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.aystudios.skincare.presentation.viewmodels.UserViewModel
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.profile_tab_profile

@Composable
fun ProfileTabProfileComponent(viewModel: UserViewModel) {

    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()

    Card(colors = CardDefaults.cardColors(AppSurfaceColor)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentDescription = null,
                painter = painterResource(Res.drawable.profile_tab_profile)
            )

            Column {

                when {

                    userProfile.isLoading -> {
                        Text("Loading...")
                    }

                    userProfile.error != null -> {
                        Text("Error loading profile")
                    }

                    else -> {
                        val item = userProfile.response

                        Text(
                            text = item.name ?: "Your Name",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W400),
                            maxLines = 1
                        )

                        Text(
                            text = item.email,
                            style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                        )
                    }

                }
            }
        }
    }
}