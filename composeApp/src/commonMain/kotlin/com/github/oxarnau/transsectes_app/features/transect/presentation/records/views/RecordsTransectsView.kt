package com.github.oxarnau.transsectes_app.features.transect.presentation.records.views

// Necessary imports
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Person2
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.intents.RecordsIntent
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.navigation.TransectRecordsGraph
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel
import org.jetbrains.compose.resources.stringResource
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.go_back

/**
 * Represents an item in the bottom navigation bar.
 *
 * @property title The label for the navigation item.
 * @property selectedIcon The icon displayed when the item is selected.
 * @property unselectedIcon The icon displayed when the item is not selected.
 * @property route The navigation route associated with this item.
 * @property intent The intent associated with the item's action.
 */
data class BottomItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Route,
    val intent: RecordsIntent,
)

/**
 * Renders the records view with a top app bar and a bottom navigation bar.
 * Handles navigation and displays different views.
 *
 * @param navController The main NavHostController for navigation.
 * @param viewModel The ViewModel for managing the records state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsTransectsView(
    navController: NavHostController,
    viewModel: RecordsViewModel,
) {
    val items = getBottomNavigationItems() // Fetch navigation items

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val navControllerGraph =
        rememberNavController() // Navigation controller for the graph

    val snackbarHostState = remember { SnackbarHostState() }

    val state = viewModel.state.collectAsStateWithLifecycle().value


    // Handle navigation actions from the ViewModel
    LaunchedEffect(viewModel.navigation) {
        viewModel.navigation.collect { route ->
            println("RecordsTransectsView Route: ${route}")
            when (route) {
                is Route.GoBack -> navController.navigate(Route.Home) {
                    popUpTo(Route.Home) { inclusive = true }
                    launchSingleTop = true
                } // Navigate to home
                is Route.DetailedTransect -> navController.navigate(route)
                else -> {
                    val currentRoute =
                        navControllerGraph.currentBackStackEntry?.destination?.route?.split(
                            '.'
                        )?.lastOrNull() ?: ""
                    if (currentRoute != route.toString()) {
//                        navControllerGraph.popBackStack()
                        navControllerGraph.navigate(route) {
                            popUpTo(0)
                            launchSingleTop =
                                true // Avoid duplicate destinations
                        }
                    }
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

    // Define the layout using Scaffold
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                { viewModel.onIntent(RecordsIntent.onGoBackClick) },
                scrollBehavior,
                "Transects"
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = items,
                navController = navControllerGraph,
                onClick = { intent -> viewModel.onIntent(intent) }
            )
        }
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
            TransectRecordsGraph(
                navControllerGraph,
                innerPadding,
                viewModel
            )
        }
    }
}

/**
 * Retrieves the bottom navigation items based on user roles or preferences.
 *
 * @return A list of [BottomItem] representing the navigation options.
 */
@Composable
fun getBottomNavigationItems(): List<BottomItem> {
    return if (true) { // Modify this condition to handle user roles dynamically
        listOf(
            BottomItem(
                "Mine",
                Icons.Filled.Person,
                Icons.Outlined.Person,
                Route.MyTransects,
                RecordsIntent.onMyTransectsClick
            ),
            BottomItem(
                "All",
                Icons.Filled.Person2,
                Icons.Outlined.Person2,
                Route.AllTransects,
                RecordsIntent.onAllTransectsClick
            ),
            BottomItem(
                "Download",
                Icons.Filled.Download,
                Icons.Outlined.Download,
                Route.DonwloadTransects,
                RecordsIntent.onDownloadClick
            ),
            BottomItem(
                "Remove",
                Icons.Filled.Remove,
                Icons.Outlined.Remove,
                Route.RemoveTransects,
                RecordsIntent.onRemoveClick
            ),
        )
    } else {
        listOf(
            BottomItem(
                "Mine",
                Icons.Filled.Person,
                Icons.Outlined.Person,
                Route.MyTransects,
                RecordsIntent.onMyTransectsClick
            ),
            BottomItem(
                "Download",
                Icons.Filled.Download,
                Icons.Outlined.Download,
                Route.DonwloadTransects,
                RecordsIntent.onDownloadClick
            ),
        )
    }
}

/**
 * Renders the top app bar with a title, navigation icon, and optional actions.
 *
 * @param emitIntent The function to handle navigation icon clicks.
 * @param scrollBehavior The behavior for handling scrolling actions.
 * @param label The label to display as the title of the top app bar.
 * @param actions A composable function for rendering optional actions in the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    emitIntent: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    label: String,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    MediumTopAppBar(
        title = {
            Text(
                label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { emitIntent() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.go_back)
                )
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}

/**
 * Renders the bottom navigation bar with multiple navigation items.
 *
 * @param items A list of [BottomItem] representing navigation options.
 * @param navController The NavHostController to handle navigation actions.
 * @param onClick A callback triggered when an item is clicked.
 */
@Composable
fun BottomNavigationBar(
    items: List<BottomItem>,
    navController: NavHostController,
    onClick: (RecordsIntent) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            AddItem(item, currentDestination, onClick)
        }
    }
}

/**
 * Renders a single item in the bottom navigation bar.
 *
 * @param item The [BottomItem] to render.
 * @param currentDestination The current destination in the navigation stack.
 * @param onClick A callback triggered when the item is clicked.
 */
@Composable
fun RowScope.AddItem(
    item: BottomItem,
    currentDestination: NavDestination?,
    onClick: (RecordsIntent) -> Unit,
) {
    val isSelected = currentDestination?.hierarchy?.any {
        (it.route?.split('.')?.lastOrNull() ?: "") == item.route.toString()
    } == true

    NavigationBarItem(
        selected = isSelected,
        onClick = { onClick(item.intent) },
        icon = {
            val icon =
                if (isSelected) item.selectedIcon else item.unselectedIcon
            Icon(icon, contentDescription = "${item.title} navigation item")
        },
        label = { Text(item.title) }
    )
}
