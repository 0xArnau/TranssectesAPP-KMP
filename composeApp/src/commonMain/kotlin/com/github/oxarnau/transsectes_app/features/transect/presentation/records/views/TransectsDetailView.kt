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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Coordinate
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

                            viewModel.onIntent(
                                RecordsIntent.onDownloadClick(
                                    csvString
                                )
                            )
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
                    state.detailedRecord?.coordinates?.let { coordinates ->
                        GoToMapsItem(
                            coordinates = coordinates,
                            snackbarHostState = snackbarHostState
                        )
                    }
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


/**
 * Displays a Google Maps direction URL with a title and an icon to copy the URL to the clipboard.
 * The URL text is selectable, and only the copy icon is clickable.
 * A snackbar notification is shown when the URL is copied to the clipboard.
 *
 * @param coordinates A list of [Coordinate] representing the route. Must include at least an origin and a destination.
 * @param snackbarHostState A [SnackbarHostState] used to display a snackbar when the URL is copied to the clipboard.
 */
@Composable
fun GoToMapsItem(
    coordinates: List<Coordinate>,
    snackbarHostState: SnackbarHostState
) {
    val clipboardManager = LocalClipboardManager.current

    // Exit early if no coordinates are provided.
    if (coordinates.isEmpty()) return

    // Extract origin, destination, and waypoints from the coordinates list.
    val origin = coordinates.first()
    val destination = coordinates.last()
    val waypoints = coordinates.drop(1).dropLast(1)
    val waypointsString =
        waypoints.joinToString("|") { "${it.latitude},${it.longitude}" }

    println("waypoints: $waypoints")
    println("waypointsString: $waypointsString")

    // Construct the Google Maps direction URL.
    val url =
        "https://www.google.com/maps/dir/?api=1&origin=${origin.latitude},${origin.longitude}&destination=${destination.latitude},${destination.longitude}&waypoints=$waypointsString"

    // State to track whether the copy icon was clicked.
    val isClicked = remember { mutableStateOf(false) }

    // Trigger a snackbar message when the URL is copied to the clipboard.
    LaunchedEffect(isClicked.value) {
        if (isClicked.value) {
            snackbarHostState.showSnackbar("URL copied to clipboard") // TODO: i18n
            isClicked.value = false
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title and selectable text section.
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Coordinates (Google Maps)", // Title for the URL.
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            SelectionContainer {
                Text(
                    text = url,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Copy icon.
        Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = "Copy URL",
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable {
                    clipboardManager.setText(AnnotatedString(url)) // Copy URL to clipboard.
                    isClicked.value = true
                }
        )
    }
}
