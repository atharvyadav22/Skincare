package org.aystudios.skincare.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.aystudios.skincare.getAppNavigator
import org.aystudios.skincare.presentation.screens.OTPScreenNavigator
import org.jetbrains.compose.resources.painterResource
import skincare.composeapp.generated.resources.Res
import skincare.composeapp.generated.resources.visibility_off
import skincare.composeapp.generated.resources.visibility_on


@Composable
fun LoginSignUpScreenComponent(isLoginScreen: Boolean, onClick: () -> Unit) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        Text(
            "Sign in to your Account",
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            "Enter you email and password to login here",
            style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
        )

        InputTextComponent("Email", "example@gmail.com", keyboardType = KeyboardType.Email)
        InputTextComponent(
            "Password",
            "Enter your password",
            keyboardType = KeyboardType.Password,
            isPassword = true
        )

        val navigator = getAppNavigator()
        Button(modifier = Modifier.fillMaxWidth(), onClick = { navigator.push(OTPScreenNavigator) }) {
            Text(if (isLoginScreen) "Login" else "Sign Up")
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

}

@Composable
fun InputTextComponent(
    title: String,
    label: String,
    keyboardType: KeyboardType,
    isPassword: Boolean = false
) {
    var text by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Text(title, style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            singleLine = true,
            textStyle = TextStyle(fontSize = 14.sp),
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