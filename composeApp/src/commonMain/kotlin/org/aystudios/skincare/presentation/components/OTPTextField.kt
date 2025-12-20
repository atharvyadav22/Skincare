package org.aystudios.skincare.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

sealed class OtpState {
    object Idle : OtpState()
    object Success : OtpState()
    object Error : OtpState()
}


@Composable
fun OtpTextField(
    otpValue: String,
    onOtpValueChange: (String) -> Unit,
    otpLength: Int = 4,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {},
    modifier: Modifier = Modifier,
    boxSize: Dp = 48.dp,
    borderSize: Dp = 2.dp,
    spacing: Dp? = null, // Optional custom spacing
    textStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(Color.Black),
    shakeMagnitude: Float = 20f,
    shakeDuration: Int = 100,
    shakeIterations: Int = 3,
    verifyOtp: suspend (String) -> Boolean // backend verification
) {


    var state by remember { mutableStateOf<OtpState>(OtpState.Idle) }
    val shakeXOffset = remember { Animatable(0f) }

    // Trigger backend verification once OTP is complete
    LaunchedEffect(otpValue) {
        if (otpValue.length == otpLength && state != OtpState.Success) {
            state = if (verifyOtp(otpValue)) OtpState.Success else OtpState.Error
        }
    }

    // Shake & callbacks
    LaunchedEffect(state) {
        when (state) {
            is OtpState.Error -> {
                onOtpValueChange("") // clear input
                shakeXOffset.animateTo(
                    targetValue = shakeMagnitude,
                    animationSpec = repeatable(
                        iterations = shakeIterations,
                        animation = tween(shakeDuration),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                shakeXOffset.snapTo(0f)
                state = OtpState.Idle
                onError()
            }

            is OtpState.Success -> onSuccess()
            else -> Unit
        }
    }

    BasicTextField(
        value = otpValue,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() } && newValue.length <= otpLength && state != OtpState.Success) {
                onOtpValueChange(newValue)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Box(modifier = modifier.offset { IntOffset(shakeXOffset.value.roundToInt(), 0) }) {
                OtpBoxes(
                    value = otpValue,
                    otpLength = otpLength,
                    state = state,
                    textStyle = textStyle,
                    borderSize = borderSize,
                    boxSize = boxSize,
                    spacing = spacing
                )
            }
        }
    )
}

@Composable
private fun OtpBoxes(
    value: String,
    otpLength: Int,
    state: OtpState,
    textStyle: TextStyle,
    borderSize: Dp,
    boxSize: Dp,
    spacing: Dp?
) {
    val borderColor = when (state) {
        is OtpState.Success -> Color(0xFF81C784)
        is OtpState.Error -> Color(0xFFE57373)
        else -> Color.Black
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (spacing != null) {
            Arrangement.spacedBy(spacing, Alignment.CenterHorizontally)
        } else {
            Arrangement.SpaceEvenly
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(otpLength) { index ->
            val digit = value.getOrNull(index)?.toString() ?: ""
            OtpBox(
                digit = digit,
                borderColor = borderColor,
                borderSize = borderSize,
                boxSize = boxSize,
                textStyle = textStyle
            )
        }
    }
}

@Composable
private fun OtpBox(
    digit: String,
    borderColor: Color,
    borderSize: Dp,
    boxSize: Dp,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.size(boxSize).border(borderSize, borderColor, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) { Text(digit, style = textStyle) }


}