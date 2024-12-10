package com.github.oxarnau.transsectes_app.features.transect.presentation.records.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransectsDetailView(
    navController: NavHostController,
    viewModel: RecordsViewModel,
) {

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val snackbarHostState = remember { SnackbarHostState() }

    val state = viewModel.state.collectAsStateWithLifecycle().value

    // Show error messages in a Snackbar if there's an error
    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }

        viewModel.clearErrorMessage()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                navController,
                scrollBehavior,
                "Transects Detail"
            )
        },
    ) { innerPadding ->
        if (state.isLoading) {
            // Display a loading indicator
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "TransectsDetailView: ${state.detailedRecord}",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}