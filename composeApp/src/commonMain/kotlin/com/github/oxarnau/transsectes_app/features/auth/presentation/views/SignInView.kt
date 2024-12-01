package com.github.oxarnau.transsectes_app.features.auth.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.SignInViewModel
import com.github.oxarnau.transsectes_app.shared.components.CustomButton
import com.github.oxarnau.transsectes_app.shared.components.CustomInput
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInView(
    navController: NavController,
    viewModel: SignInViewModel = koinViewModel(),
) {
    val navigationFlow = remember { viewModel.navigation }

    // Collect the navigation flow and navigate when appropriate
    LaunchedEffect(navigationFlow) {
        navigationFlow.collect { route -> navController.navigate(route) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Add padding around the column for spacing
    ) {
        Text(
            text = "Sign In", // TODO: Add internationalization (i18n)
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 32.dp) // Spacing for the title
        )

        // Email input field
        CustomInput(
            label = "Email", // TODO: Add internationalization (i18n)
            placeholder = "Enter your email",// TODO: Add internationalization (i18n)
            inputType = "email", // Specify the input type as email
            modifier = Modifier.fillMaxWidth(), // Make it take up the full width
            onValueChange = { email -> viewModel.onEmailChange(email) } // Handle email input changes in the ViewModel
        )

        // Password input field
        CustomInput(
            label = "Password",// TODO: Add internationalization (i18n)
            placeholder = "Enter your password", // TODO: Add internationalization (i18n)
            inputType = "password", // Specify the input type as password
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp), // Add spacing between email and password fields
            onValueChange = { password -> viewModel.onPasswordChange(password) } // Handle password input changes in the ViewModel
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp) // Add spacing above the buttons
        ) {
            // Back Button
            CustomButton(
                text = "Back", // TODO: Add internationalization (i18n)
                goto = { navController.popBackStack() } // Navigate to previous screen when clicked
            )

            // Spacer between buttons
            Spacer(modifier = Modifier.width(16.dp))

            // Next Button (Submit form and navigate)
            CustomButton(
                text = "Next", // TODO: Add internationalization (i18n)
                goto = { viewModel.submitSignIn() } // Submit the sign-in form and handle navigation
            )
        }
    }
}
