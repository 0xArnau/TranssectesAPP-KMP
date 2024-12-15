package com.github.oxarnau.transsectes_app.features.transect.presentation.start.views

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartTransectView(
    navController: NavHostController,
) {
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                { navController.popBackStack() },
                scrollBehavior,
                "Start/Stop transect",

                )
        },
    ) { innerPadding ->

    }
}
