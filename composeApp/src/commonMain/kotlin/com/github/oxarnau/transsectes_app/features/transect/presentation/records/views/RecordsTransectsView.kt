package com.github.oxarnau.transsectes_app.features.transect.presentation.records.views

import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.navigation.TransectRecordsGraph
import org.jetbrains.compose.resources.stringResource
import transsectesapp.composeapp.generated.resources.Res
import transsectesapp.composeapp.generated.resources.go_back

/**
 * Data class representing an item in the bottom navigation bar.
 *
 * This class holds information about each navigation item:
 * - title: The text displayed for the item.
 * - selectedIcon: The icon when the item is selected.
 * - unselectedIcon: The icon when the item is not selected.
 * - route: The route associated with this navigation item.
 */
data class BottomItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Route,
)

/**
 * Composable function for rendering the records view with a top app bar
 * and a bottom navigation bar. It handles navigation and displaying different views.
 *
 * @param navController The NavHostController used to handle navigation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsTransectsView(navController: NavHostController) {
    // Get the items for the bottom navigation
    val items = getBottomNavigationItems()

    // State for managing the selected item index
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    // Top app bar scroll behavior
    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val navControllerGraph = rememberNavController()


    // Scaffold for layout
    Scaffold(
        topBar = {
            // Render the top app bar with scroll behavior
            TopAppBar(navController, scrollBehavior)
        },
        bottomBar = {
            // Render the bottom navigation bar with the items
            BottomNavigationBar(
                items,
                navControllerGraph
            )
        }
    ) { innerPadding ->
        // Render the navigation graph based on the selected route
        TransectRecordsGraph(navControllerGraph, innerPadding)
    }
}

/**
 * Retrieves the list of bottom navigation items based on the current user's role (e.g., technical).
 *
 * The items will change depending on the role. For example, the "Remove" option is available
 * for users with certain privileges.
 *
 * @return A list of [BottomItem] objects representing the items to be displayed in the bottom navigation bar.
 */
@Composable
fun getBottomNavigationItems(): List<BottomItem> {
    return if (true) {  // Change the condition based on the user's role
        // Full list of items for users with specific roles
        listOf(
            BottomItem(
                "Mine",
                Icons.Filled.Person,
                Icons.Outlined.Person,
                Route.MyTransects
            ),
            BottomItem(
                "All",
                Icons.Filled.Person2,
                Icons.Outlined.Person2,
                Route.AllTransects
            ),
            BottomItem(
                "Download",
                Icons.Filled.Download,
                Icons.Outlined.Download,
                Route.DonwloadTransects
            ),
            BottomItem(
                "Remove",
                Icons.Filled.Remove,
                Icons.Outlined.Remove,
                Route.RemoveTransects
            )
        )
    } else {
        // Restricted list of items for users with fewer privileges
        listOf(
            BottomItem(
                "Mine",
                Icons.Filled.Person,
                Icons.Outlined.Person,
                Route.MyTransects
            ),
            BottomItem(
                "Download",
                Icons.Filled.Download,
                Icons.Outlined.Download,
                Route.DonwloadTransects
            )
        )
    }
}

/**
 * Composable function for rendering the TopAppBar.
 * The TopAppBar includes the title and a navigation icon for going back.
 *
 * @param navController The NavHostController used to handle navigation.
 * @param scrollBehavior The scroll behavior for the top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    // MediumTopAppBar is a Material Design component that supports scroll behavior
    MediumTopAppBar(
        title = {
            Text(
                text = "Transects",  // Title of the app bar (TODO: Internationalization)
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            // Back button icon to navigate up in the stack
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.go_back)
                )
            }
        },
        scrollBehavior = scrollBehavior  // Apply the scroll behavior to the top app bar
    )
}

/**
 * Composable function for rendering the bottom navigation bar with items.
 * The navigation bar allows the user to switch between different views.
 *
 * @param items The list of [BottomItem] to be displayed in the bottom navigation bar.
 * @param navController The NavHostController used to handle navigation.
 */
@Composable
fun BottomNavigationBar(
    items: List<BottomItem>,
    navController: NavHostController
) {
    // Get the current navigation destination from the back stack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Render the navigation bar
    NavigationBar {
        // For each item, render it inside the navigation bar
        items.forEach { item ->
            // Add each item in a row
            AddItem(
                item,
                currentDestination,
                navController
            )
        }
    }
}

/**
 * Composable function to render a single item in the bottom navigation bar.
 *
 * @param item The [BottomItem] to render.
 * @param currentDestionation The current destination in the navigation stack.
 * @param navController The NavHostController used to navigate to the destination.
 */
@Composable
fun RowScope.AddItem(
    item: BottomItem,
    currentDestionation: NavDestination?,
    navController: NavHostController,
) {
    // Check if the current destination matches the item's route to determine if it's selected
    val isSelected = currentDestionation?.hierarchy?.any {
        println(
            "1 - AddItem(${item.route}) -> isSelected: ${it.route} | ${
                (it.route?.split('.')?.lastOrNull() ?: "")
            }"
        )
        (it.route?.split('.')?.lastOrNull() ?: "") == item.route.toString()
    } == true

    println("2 - AddItem -> isSelected: $isSelected, currentDestionation: ${currentDestionation.toString()}, navController: $navController")


    // Render the navigation bar item
    NavigationBarItem(
        selected = isSelected,  // Highlight if selected
        onClick = { navController.navigate(item.route) },  // Navigate when clicked
        icon = {
            // Set the icon based on whether the item is selected
            val icon =
                if (isSelected) item.selectedIcon else item.unselectedIcon
            val description =
                if (isSelected) "${item.title} is selected" else "${item.title} is not selected"

            Icon(
                imageVector = icon,  // Set the icon based on selection state
                contentDescription = description,  // Provide a description for accessibility
            )
        },
        label = { Text(item.title) },  // Label for the item
    )
}
