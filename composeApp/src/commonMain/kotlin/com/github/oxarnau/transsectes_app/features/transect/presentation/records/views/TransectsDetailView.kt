package com.github.oxarnau.transsectes_app.features.transect.presentation.records.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.toCSV
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.intents.RecordsIntent
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

    // Handle navigation actions from the ViewModel
    LaunchedEffect(viewModel.navigation) {
        viewModel.navigation.collect { route ->
            println("TransectsDetailView Route: \${route}")
            if (route == Route.GoBack) {
                navController.navigate(Route.RecordsTransects) {
                    popUpTo(Route.RecordsTransects) { inclusive = true }
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

        viewModel.clearErrorMessage()
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                { viewModel.onIntent(RecordsIntent.onGoBackClick) },
                scrollBehavior,
                "Transects Detail",
                actions = {
                    IconButton(onClick = {
                        state.detailedRecord?.let {
                            val csvString = it.toCSV()
                            // TODO
                            println("CSV Generated: \n${csvString}") // Replace with file download logic
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Download,
                            contentDescription = "Download CSV"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        if (state.isLoading) {
            // Display a loading indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
            ) {
                item {
                    TitleSubtitle(
                        "Date", // TODO: i18n
                        state.detailedRecord?.createdAt.toString()
                    )
                }
                item {
                    TitleSubtitle(
                        "Administrative Area", // TODO: i18n
                        formatSubtitle(
                            state.detailedRecord?.administrativeAreaFirst,
                            state.detailedRecord?.administrativeAreaLast
                        )
                    )
                }
                item {
                    TitleSubtitle(
                        "Locality Area", // TODO: i18n
                        formatSubtitle(
                            state.detailedRecord?.localityAreaFirst,
                            state.detailedRecord?.localityAreaLast
                        )
                    )
                }
                item {
                    TitleSubtitle(
                        "Sub-Administrative Area", // TODO: i18n
                        formatSubtitle(
                            state.detailedRecord?.subAdministrativeAreaFirst,
                            state.detailedRecord?.subAdministrativeAreaLast
                        )
                    )
                }
                item {
                    TitleSubtitle(
                        "Created By", // TODO: i18n
                        state.detailedRecord?.createdBy ?: "N/A"
                    )
                }
                item {
                    TitleSubtitle(
                        "Informed People", // TODO: i18n
                        state.detailedRecord?.informedPeople?.toString()
                            ?: "N/A"
                    )
                }
                item {
                    TitleSubtitle(
                        "Observations", // TODO: i18n
                        state.detailedRecord?.observations ?: "N/A"
                    )
                }
                item {
                    TitleSubtitle(
                        "Tractor Used", // TODO: i18n
                        if (state.detailedRecord?.tractor == true) "Yes" else "No"
                    )
                }

                item {
                    GoToMapsItem()
                }
            }
        }
    }
}

/**
 * Displays a title and a subtitle in a vertical layout.
 *
 * @param title The title to display.
 * @param subtitle The subtitle to display below the title.
 */
@Composable
fun TitleSubtitle(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = subtitle)
    }
}

/**
 * Formats two strings into a single subtitle.
 *
 * If both strings are null or blank, it returns "N/A".
 * If the strings are equal, it returns only one of them.
 * If the strings are different, it concatenates them with a dash.
 *
 * @param first The first string.
 * @param last The second string.
 * @return A formatted subtitle string.
 */
fun formatSubtitle(first: String?, last: String?): String {
    return when {
        first.isNullOrBlank() && last.isNullOrBlank() -> "N/A"
        first == last -> first ?: "N/A"
        else -> "${first ?: ""} - ${last ?: ""}"
    }
}


@Composable
fun GoToMapsItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { println("GoToMapsItem clicked") }, // TODO
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "See transect on map (coordinates)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        ) // TODO: i18n
        Icon(Icons.Filled.ChevronRight, contentDescription = null) // TODO
    }
}
