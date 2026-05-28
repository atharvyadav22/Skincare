package org.aystudios.skincare.presentation.screens.configure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.core.screen.Screen
import io.github.alexzhirkevich.compottie.*
import kotlinx.coroutines.delay
import org.aystudios.skincare.presentation.components.getAppRootNavigator
import org.aystudios.skincare.presentation.screens.auth.LoginSignUpScreenNavigator
import org.aystudios.skincare.presentation.viewmodels.StartupViewModel
import org.aystudios.skincare.ui.theme.AppPrimaryColor
import org.aystudios.skincare.ui.theme.AppScaffold
import org.aystudios.skincare.utils.BaseUrlRefresher
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import skincare.shared.generated.resources.Res

object ApiStartupScreenNavigator : Screen {
    @Composable
    override fun Content() {
        ApiStartupScreen()
    }
}

@Composable
fun ApiStartupScreen() {
    val navigator = getAppRootNavigator()
    ApiStartupScreenContent() {
        navigator?.replace(LoginSignUpScreenNavigator)
    }
}

@Composable
fun ApiStartupScreenContent(
    viewModel: StartupViewModel = koinViewModel(),
    baseUrlManager: BaseUrlRefresher = koinInject(),
    onSuccess: () -> Unit
) {

    val state by viewModel.startUpState.collectAsState()

    var counter by remember { mutableIntStateOf(20) }

    LaunchedEffect(Unit) {
        while (counter > 0) {
            delay(1000)
            counter--
        }
    }

    LaunchedEffect(state.response) {
        if (state.response == "API Called") {
            onSuccess()
        }
    }

    var countWake by remember { mutableIntStateOf(1) }
    LaunchedEffect(Unit){
        while (true){
            delay(500)
            countWake++
            if(countWake > 3) countWake = 1
        }
    }

    AppScaffold(showTopBar = false) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Text(
                "🧘Waking Up Server" + ".".repeat(countWake),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                color = AppPrimaryColor
            )

            Text(
                "Render may take ~1 minute to start",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(8.dp))

            Text(counter.toFormattedTimer())

            Spacer(Modifier.height(16.dp))

            val composition by rememberLottieComposition {
                LottieCompositionSpec.DotLottie(
                    Res.readBytes("drawable/meditation.lottie")
                )
            }

            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = Compottie.IterateForever
            )

            Image(
                painter = rememberLottiePainter(
                    composition = composition,
                    progress = { progress }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )

            Spacer(Modifier.height(16.dp))


            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text("Error: $it", color = Color.Red)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.test() },
                enabled = counter == 0 && !state.isLoading
            ) {
                Text("Retry")
            }

            Spacer(Modifier.height(16.dp))

            Text("Use Local Server Instead?")

            val savedUrl = baseUrlManager.getLocalBaseUrl()

            UrlInputWithSuggestion(
                savedUrl = savedUrl,
                onSave = { url ->
                    baseUrlManager.setLocalBaseUrl(fixUrl(url))
                    viewModel.test()
                }
            )
        }
    }
}

private fun Int.toFormattedTimer(): String {
    val minutes = this / 60
    val seconds = this % 60

    val remainingSeconds = seconds.toString().padStart(2, '0')
    val remainingMinutes = minutes.toString().padStart(2, '0')

    return "$remainingMinutes:$remainingSeconds"
}

private fun fixUrl(url: String): String {
    return "http://$url:8080/api"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UrlInputWithSuggestion(
    savedUrl: String?,
    onSave: (String) -> Unit
) {
    var input by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally){

        ExposedDropdownMenuBox(
            expanded = expanded && !savedUrl.isNullOrBlank(),
            onExpandedChange = { expanded = it }
        ) {

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter API URL") },
                placeholder = { Text("192.168.1.5") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .onFocusChanged {
                        expanded = it.isFocused
                    }
            )

            if (!savedUrl.isNullOrBlank()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(savedUrl) },
                        onClick = {
                            input = savedUrl
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (input.isNotBlank()) {
                    onSave(input)
                }
            }
        ) {
            Text("Use This Server")
        }
    }
}