package com.github.oxarnau.transsectes_app.features.auth.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.auth.presentation.intents.AuthIntent
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.AuthViewModel
import com.github.oxarnau.transsectes_app.shared.presentation.components.CustomButton
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.imatge_tortuga

/**
 * Displays the authentication screen with navigation options and loading indicator.
 *
 * @param navController Navigation controller for handling navigation actions.
 * @param viewModel ViewModel for managing authentication logic and state.
 */
@Composable
fun AuthView(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    // Observe navigation flow
    LaunchedEffect(viewModel.navigation) {
        viewModel.navigation.collect { route ->
            navController.navigate(route) {
                popUpTo(Route.Auth) { inclusive = true }
            }
        }
    }

    Scaffold { innerPadding ->
        if (state.isLoading) {
            // Display a loading indicator
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Display the authentication options
            val isDarkMode = isSystemInDarkTheme()
            val newColor = if (isDarkMode) null else Color.Black

            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.weight(0.5f))

                Image(
                    painterResource(Res.drawable.imatge_tortuga),
                    contentDescription = "White turtle image on transparent background", // TODO: i18n
                    colorFilter = newColor?.let {
                        ColorFilter.tint(
                            color = it,
                            blendMode = BlendMode.SrcIn
                        )
                    },
                    modifier = Modifier.align(Alignment.End).size(300.dp)
                )

                Text(
                    text = "Transsectes APP",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.safeContent)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.weight(2f))

                Column {
                    CustomButton(
                        text = "Create account",
                        goto = { viewModel.onIntent(AuthIntent.onSignUpClick) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    CustomButton(
                        text = "Sign In",
                        goto = { viewModel.onIntent(AuthIntent.onSignInClick) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
