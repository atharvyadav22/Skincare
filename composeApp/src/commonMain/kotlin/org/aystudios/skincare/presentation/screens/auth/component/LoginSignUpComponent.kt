package org.aystudios.skincare.presentation.screens.auth.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.aystudios.skincare.presentation.components.InputTextComponent
import org.aystudios.skincare.presentation.components.getAppNavigator
import org.aystudios.skincare.presentation.navigation.AppBottomNavigator
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel


@Composable
fun LoginSignUpScreenComponent(
    loginViewModel: LoginViewModel,
    isLoginScreen: Boolean,
    onClick: () -> Unit
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val state by loginViewModel.loginState.collectAsState()
    val navigator = getAppNavigator()
    val isLoading = state.isLoading

    LaunchedEffect(state.isSuccess){
        if (state.isSuccess) {
            navigator.replaceAll(AppBottomNavigator)
        }
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Text(
                if (isLoginScreen) "Welcome Back Login" else "Sign in to your Account",
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                "Enter you email and password to login here",
                style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
            )

            Spacer(Modifier.height(4.dp))
            InputTextComponent(
                "Email",
                "example@gmail.com",
                text = email,
                keyboardType = KeyboardType.Email
            ) { email = it }
            InputTextComponent(
                "Password",
                "Enter your password",
                text = password,
                keyboardType = KeyboardType.Password,
                isPassword = true
            ) { password = it }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                onClick = { loginViewModel.login(email, password) }
            )
            {

                Text(if (isLoginScreen) "Login" else if (isLoading) "Logging..." else "Sign Up")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text("Or login with", style = MaterialTheme.typography.labelLarge.copy(Color.Gray))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }


            val annotatedText = buildAnnotatedString {
                withStyle(MaterialTheme.typography.labelLarge.copy(Color.Gray).toSpanStyle()) {
                    val helper = if (isLoginScreen) "Don't" else "Already"
                    append(" $helper Have An Account? ")
                }

                withStyle(
                    MaterialTheme.typography.labelLarge.copy(
                        Color(0xFF4D81E7),
                        textDecoration = TextDecoration.Underline
                    ).toSpanStyle()
                ) {
                    append(if (isLoginScreen) "Sign Up" else "Login")
                }
            }
            Text(
                annotatedText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onClick() })

        }

//        AnimatedVisibility(
//            visible = showError,
//            enter = fadeIn(
//                animationSpec = tween(
//                    durationMillis = 300,
//                    easing = FastOutSlowInEasing
//                )
//            ) + slideInVertically(
//                animationSpec = tween(300),
//                initialOffsetY = { -it }
//            ),
//            exit = fadeOut(
//                animationSpec = tween(
//                    durationMillis = 300,
//                    easing = FastOutSlowInEasing
//                )
//            ) + slideOutVertically(
//                animationSpec = tween(300),
//                targetOffsetY = { -it }
//            ),
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//        ) {
//            ErrorBannerComponent( (state as ApiResult.Error).message )
//        }

    }
}