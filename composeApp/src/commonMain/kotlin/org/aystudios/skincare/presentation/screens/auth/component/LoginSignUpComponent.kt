package org.aystudios.skincare.presentation.screens.auth.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.aystudios.skincare.core.network.ApiResult
import org.aystudios.skincare.presentation.components.getAppNavigator
import org.aystudios.skincare.presentation.navigation.AppBottomNavigator
import org.aystudios.skincare.presentation.viewmodels.LoginViewModel
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.visibility_off
import skincare.composeapp.generated.resources.visibility_on


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
    val isLoading = state is ApiResult.Loading

    var showError by remember { mutableStateOf(false) }
    LaunchedEffect(state) {
        if (state is ApiResult.Error) {
            showError = true
            delay(3000)
            showError = false
        }
    }

    LaunchedEffect(state) {
        if (state is ApiResult.Success) {
            navigator.replaceAll(AppBottomNavigator)
        }
    }

    Box {
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

        AnimatedVisibility(
            visible = showError,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + slideInVertically(
                animationSpec = tween(300),
                initialOffsetY = { -it }
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + slideOutVertically(
                animationSpec = tween(300),
                targetOffsetY = { -it }
            ),
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            ErrorBannerComponent( (state as ApiResult.Error).message )
        }

    }

}

@Composable
fun InputTextComponent(
    title: String,
    label: String,
    text: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    onValueChanged: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Text(title, style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = text,
            onValueChange = { onValueChanged(it) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
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