package com.github.oxarnau.transsectes_app.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.oxarnau.transsectes_app.home.presentation.views.HomeView
import com.github.oxarnau.transsectes_app.settings.views.SettingsView

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home
    ) {
        composable<Route.Home> {
            HomeView() { navController.navigate(Route.Settings) }
        }

        composable<Route.Settings> {
            SettingsView { navController.popBackStack() }
        }
    }
}