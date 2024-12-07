package com.github.oxarnau.transsectes_app.features.transect.presentation.records.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.oxarnau.transsectes_app.app.navigation.Route
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.AllTransectsView
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.DownloadTransectsView
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.MyTransectsView
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.RemoveTransectsView

@Composable
fun TransectRecordsGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    navController.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller
            .currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")

        println("NavController: TransectRecordsGraph - BackStack: $routes")
    }

    NavHost(
        navController = navController,
        startDestination = Route.MyTransects,
    ) {
        composable<Route.MyTransects> {
            MyTransectsView(innerPadding)
        }

        composable<Route.AllTransects> {
            AllTransectsView(innerPadding)
        }

        composable<Route.DonwloadTransects> {
            DownloadTransectsView(innerPadding)
        }

        composable<Route.RemoveTransects> {
            RemoveTransectsView(innerPadding)
        }
    }
}