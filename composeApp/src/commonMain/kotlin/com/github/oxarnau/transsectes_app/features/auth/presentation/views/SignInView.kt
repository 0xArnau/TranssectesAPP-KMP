package com.github.oxarnau.transsectes_app.features.auth.presentation.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.SignInViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInView(
    navController: NavController,
    viewModel: SignInViewModel = koinViewModel(),
) {
    val navigationFlow = remember { viewModel.navigation }

    LaunchedEffect(navigationFlow) {
        navigationFlow.collect { route -> navController.navigate(route) }
    }
}
