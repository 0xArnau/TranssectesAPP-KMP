package com.github.oxarnau.transsectes_app.features.transect.presentation.records.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.intents.RecordsIntent
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.states.RecordsOwner
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.RemoveTransectsView
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.TransectsListView

@Composable
fun TransectRecordsGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    recordsViewModel: RecordsViewModel,
) {

    val state by recordsViewModel.state.collectAsState()

    val startDestination = when (state.recordsOwner) {
        RecordsOwner.TECHNICIAN -> Route.AllTransects
        else -> Route.MyTransects
    }

    navController.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller
            .currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")

        println("NavController: TransectRecordsGraph - BackStack: $routes")
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<Route.MyTransects> {
            TransectsListView(
                innerPadding,
                recordsViewModel,
                RecordsIntent.fetchMyTransects
            )
        }

        composable<Route.AllTransects> {
            TransectsListView(
                innerPadding,
                recordsViewModel,
                RecordsIntent.fetchAllTransects
            )
        }

        composable<Route.RemoveTransects> {
            RemoveTransectsView(innerPadding)
        }
    }
}