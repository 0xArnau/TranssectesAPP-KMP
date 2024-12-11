package com.github.oxarnau.transsectes_app.features.settings.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.settings.presentation.intents.SettingsIntent
import com.github.oxarnau.transsectes_app.features.settings.presentation.viewmodels.SettingsViewModel
import com.github.oxarnau.transsectes_app.shared.presentation.components.BottomAppBarWithLogo
import com.github.oxarnau.transsectes_app.shared.presentation.components.CustomButton
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.go_back
import transsectesapp.composeapp.generated.resources.settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val navigationFlow = remember { viewModel.navigation }
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state.collectAsStateWithLifecycle().value

    // Collect the navigation flow and navigate when appropriate
    LaunchedEffect(navigationFlow) {
        navigationFlow.collect { route ->
            if (route == Route.Home) navController.popBackStack()

            if (route == Route.Auth) {
                navController.navigate(Route.Auth) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
    }

    // Show error messages in a Snackbar if there's an error
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.settings),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onIntent(SettingsIntent.GoBackClick) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.go_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBarWithLogo()
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = innerPadding
        ) {
            item {
                UserRole(isTechnician = state.isTechnician)
            }

            item {
                EmailWithToggleVisibility(email = state.email ?: "")
            }

            item {
                CustomButton(
                    text = "Sign Out", // TODO
                    goto = { viewModel.onIntent(SettingsIntent.SignOutClick) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Displays a user's role with an icon and a corresponding text.
 * The role is determined by the [isTechnician] parameter, where the text will
 * display "Technician" if the user is a technician, and "User" otherwise.
 *
 * @param isTechnician Boolean value indicating whether the user is a technician or not.
 * @param modifier Optional [Modifier] to customize the composable's layout.
 */
@Composable
fun UserRole(isTechnician: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "Person icon",
            modifier = Modifier.size(128.dp)
        )
        Text(
            text = if (isTechnician) "Technician" else "User",
            textAlign = TextAlign.Center
        )
    }
}

/**
 * A composable that displays the email address with a toggleable visibility feature.
 *
 * @param email The email to display.
 * @param modifier Modifier to customize the composable layout.
 */
@Composable
fun EmailWithToggleVisibility(email: String, modifier: Modifier = Modifier) {
    // State to track whether the email is visible or hidden
    val isEmailVisible = remember { mutableStateOf(false) }

    // Convert the email to asterisks when hidden
    val privateEmail = convertStringToAsterisk(email)

    // A row to display the email with icons for leading (email) and trailing (visibility toggle)
    Row(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Leading icon (email)
        Icon(
            imageVector = Icons.Filled.Email,
            contentDescription = "Email Icon",
        )

        // Display email as text or as asterisks based on isEmailVisible state
        Text(
            text = if (isEmailVisible.value) email else privateEmail,
        )

        // Trailing icon (visibility toggle)
        IconButton(onClick = { isEmailVisible.value = !isEmailVisible.value }) {
            Icon(
                imageVector = if (isEmailVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                contentDescription = if (isEmailVisible.value) "Hide Email" else "Show Email"
            )
        }
    }
}

/**
 * Converts a string to asterisks. Used to hide email addresses.
 *
 * @param str The string to convert.
 * @return A string of asterisks of the same length as the original string.
 */
fun convertStringToAsterisk(str: String): String {
    return str.map { "*" }.joinToString(separator = "")
}

