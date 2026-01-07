package org.aystudios.skincare.presentation.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.delay
import org.aystudios.skincare.presentation.components.getAppNavigator
import org.aystudios.skincare.presentation.screens.auth.component.OtpTextField
import org.aystudios.skincare.presentation.navigation.AppBottomNavigator
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppScaffold
import org.jetbrains.compose.ui.tooling.preview.Preview

object OTPScreenNavigator : Screen {
    @Composable
    override fun Content() {
        OTPScreen()
    }
}

@Preview
@Composable
fun OTPScreen() {

    var otpValue by remember { mutableStateOf("") }

    AppScaffold(showTopBar = false) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text(
                    "Verify Your Code",
                    style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold)
                )

                val annotatedText = buildAnnotatedString {

                    withStyle(TextStyle(Color.Gray).toSpanStyle()) { append("Please Enter your code that sent to your mail ") }
                    withStyle(TextStyle(AppPrimaryColor).toSpanStyle()) { append(" example@gmail.com") }
                }
                Text(
                    annotatedText,
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.Gray)
                )

                Spacer(Modifier.height(4.dp))

                OtpTextField(
                    modifier = Modifier.padding(vertical = 12.dp),
                    otpValue = otpValue,
                    onOtpValueChange = { otpValue = it },
                    otpLength = 4,
                    verifyOtp = { enteredOtp ->
                        enteredOtp == "123456"
                        //Returns Boolean to verify
                    },
                    onSuccess = { /* ✅ OTP verified Perform Navigation Logic*/ },
                    onError = { /* ❌ Wrong OTP Call Resent Logic */ }
                )

                var counter by rememberSaveable { mutableIntStateOf(15) }

                LaunchedEffect(counter) {

                    while (counter > 0) {
                        delay(1000)
                        counter--
                    }
                }

                val resendText = buildAnnotatedString {
                    withStyle(TextStyle(color = Color.Gray).toSpanStyle()) {
                        append("Didn't Receive An OTP? ")
                    }

                    withStyle(
                        TextStyle(
                            Color(0xFF4D81E7),
                            textDecoration = TextDecoration.Underline
                        ).toSpanStyle()
                    ) {
                        if(counter > 0 ) append("Resend in $counter") else append("Resend")
                    }
                }

                val navigator = getAppNavigator()
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navigator.push(AppBottomNavigator) }) {
                    Text("Verify")
                }

                Text(
                    resendText,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    ).clickable(enabled = counter == 0) {
                        counter = 15
                    }
                )
            }


        }
    }
}

