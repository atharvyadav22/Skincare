package org.aystudios.skincare.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import org.aystudios.skincare.data.remote.dto.UserProfileRequestDTO
import org.aystudios.skincare.presentation.components.AppButtonComponent
import org.aystudios.skincare.presentation.components.InputTextComponent
import org.aystudios.skincare.presentation.components.getAppNavigator
import org.aystudios.skincare.presentation.viewmodels.UserViewModel
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.ui.theme.AppSurfaceColor
import org.aystudios.skincare.ui.theme.AppTopBar
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.add
import skincare.composeapp.generated.resources.profile_tab_profile

data class EditProfileScreenNavigator(val viewModel: UserViewModel) : Screen {
    @Composable
    override fun Content() {
        EditProfileScreen(viewModel)
    }

}

@Composable
fun EditProfileScreen(viewModel: UserViewModel) {

    AppScaffold(topBarTitle = "Edit Profile", isZeroPaddingValues = true) {
        Box(
            modifier = Modifier.fillMaxSize().background(AppPrimaryColor)
        ) {

            val backgroundHeight = 180.dp
            val profileSize = 96.dp
            val profileRadius = profileSize / 2
            val cardCorner = 28.dp


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (backgroundHeight - profileRadius)),
                colors = CardDefaults.cardColors(AppSurfaceColor),
                shape = RoundedCornerShape(topStart = cardCorner, topEnd = cardCorner)
            ) {
                InputEditFields(viewModel)
            }

            Box(
                modifier = Modifier.size(profileSize)
                    .align(Alignment.TopCenter)
                    .offset(y = (backgroundHeight - profileSize))
            ) {
                Image(
                    modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color.Gray),
                    painter = painterResource(Res.drawable.profile_tab_profile),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Icon(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    painter = painterResource(Res.drawable.add),
                    contentDescription = null
                )

            }
            AppTopBar(title = "")

        }

    }
}

@Composable
private fun InputEditFields(viewModel: UserViewModel) {

    val updateState by viewModel
        .updateUserProfile
        .collectAsStateWithLifecycle()

    val profileState by viewModel
        .userProfile
        .collectAsStateWithLifecycle()

    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(profileState.response) {
        profileState.response?.let { response ->
            name = response.name
            phone = response.phoneNo
            address = response.address
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 88.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 🔹 Show loading overlay / indicator
        if (profileState.isLoading) {
            Text("Loading profile...")
        }

        // 🔹 Show error (does NOT overwrite form)
        profileState.error?.let {
            Text(
                text = it,
                color = Color.Red
            )
        }

        InputTextComponent(
            "Name",
            "Enter your name",
            text = name,
            keyboardType = KeyboardType.Text,
            enabled = !profileState.isLoading
        ) { name = it }

        InputTextComponent(
            "Phone",
            "+91",
            text = phone,
            keyboardType = KeyboardType.Phone,
            enabled = !profileState.isLoading
        ) {
            if (it.length <= 10) phone = it
        }

        InputTextComponent(
            "Address",
            "Enter your address",
            text = address,
            keyboardType = KeyboardType.Text,
            enabled = !profileState.isLoading
        ) { address = it }

        AppButtonComponent(
            text = if (updateState.isLoading) "Saving..." else "Save",
            enabled = !updateState.isLoading
        ) {
            viewModel.updateUserProfile(
                UserProfileRequestDTO(name = name, phoneNo =  phone, address =  address)
            )
        }

        val navigator = getAppNavigator()
        if (updateState.isSuccess) {
            navigator.pop()
            viewModel.resetUpdateProfileState()
        }

    }
}

