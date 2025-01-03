package com.github.oxarnau.transsectes_app.features.auth.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.auth.presentation.intents.SignInIntent
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.SignInViewModel
import com.github.oxarnau.transsectes_app.shared.presentation.components.CustomButton
import com.github.oxarnau.transsectes_app.shared.presentation.components.CustomInput
import org.koin.compose.viewmodel.koinViewModel

/**
 * SignInView Composable that displays the UI for the Sign In screen.
 * It collects user input, interacts with the ViewModel, and shows error messages.
 *
 * @param navController The navigation controller to navigate between screens.
 * @param viewModel The SignInViewModel that handles the business logic for Sign In.
 */
@Composable
fun SignInView(
    navController: NavController,
    viewModel: SignInViewModel = koinViewModel(),
) {
    val navigationFlow = remember { viewModel.navigation }
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state.collectAsStateWithLifecycle().value

    // Collect the navigation flow and navigate when appropriate
    LaunchedEffect(navigationFlow) {
        navigationFlow.collect { route ->
            navController.navigate(route) {
                popUpTo(Route.SignIn) { inclusive = true }
            }
        }
    }

    // Show error messages in a Snackbar if there's an error
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }

        viewModel.clearErrorMessage()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Sign In", // TODO: Add internationalization (i18n)
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email input field
            CustomInput(
                label = "Email", // TODO: Add internationalization (i18n)
                placeholder = "Enter your email", // TODO: Add internationalization (i18n)
                inputType = "email",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email ->
                    viewModel.onIntent(
                        SignInIntent.onEmailChange(email)
                    )
                }
            )

            // Password input field
            CustomInput(
                label = "Password", // TODO: Add internationalization (i18n)
                placeholder = "Enter your password", // TODO: Add internationalization (i18n)
                inputType = "password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onValueChange = { password ->
                    viewModel.onIntent(
                        SignInIntent.onPasswordChange(password)
                    )
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Back Button
                CustomButton(
                    text = "Back", // TODO: Add internationalization (i18n)
                    goto = { navController.popBackStack() }
                )

                // Next Button (Submit form and navigate)
                CustomButton(
                    text = "Next", // TODO: Add internationalization (i18n)
                    goto = { viewModel.onIntent(SignInIntent.onNextClick) }
                )
            }
        }
    }
}
