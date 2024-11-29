package com.github.oxarnau.transsectes_app.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.oxarnau.transsectes_app.contact.views.ContactView
import com.github.oxarnau.transsectes_app.home.presentation.views.HomeView
import com.github.oxarnau.transsectes_app.howto.views.HowTo1
import com.github.oxarnau.transsectes_app.howto.views.HowTo2
import com.github.oxarnau.transsectes_app.howto.views.HowTo3
import com.github.oxarnau.transsectes_app.howto.views.HowTo4
import com.github.oxarnau.transsectes_app.howto.views.HowTo5
import com.github.oxarnau.transsectes_app.howto.views.HowTo6
import com.github.oxarnau.transsectes_app.howto.views.HowTo7
import com.github.oxarnau.transsectes_app.settings.views.SettingsView

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Home
    ) {
        composable<Route.Home> {
            HomeView(
                navigate2HowTo = { navController.navigate(Route.HowTo1) },
                navigate2Contact = { navController.navigate(Route.Contact) }
            ) { navController.navigate(Route.Settings) }
        }

        composable<Route.Settings> {
            SettingsView { navController.popBackStack() }
        }

        composable<Route.Contact> {
            ContactView { navController.popBackStack() }
        }

        composable<Route.HowTo1> {
            HowTo1(
                goBack = { navController.popBackStack() },
                goNext = { navController.navigate(Route.HowTo2) }
            )
        }

        composable<Route.HowTo2> {
            HowTo2(
                goBack = { navController.popBackStack() },
                goNext = { navController.navigate(Route.HowTo3) }
            )
        }

        composable<Route.HowTo3> {
            HowTo3(
                goBack = { navController.popBackStack() },
                goNext = { navController.navigate(Route.HowTo4) }
            )
        }

        composable<Route.HowTo4> {
            HowTo4(
                goBack = { navController.popBackStack() },
                goNext = { navController.navigate(Route.HowTo5) }
            )
        }

        composable<Route.HowTo5> {
            HowTo5(
                goBack = { navController.popBackStack() },
                goNext = { navController.navigate(Route.HowTo6) }
            )
        }

        composable<Route.HowTo6> {
            HowTo6(
                goBack = { navController.popBackStack() },
                goNext = { navController.navigate(Route.HowTo7) }
            )
        }

        composable<Route.HowTo7> {
            HowTo7(
                goBack = { navController.popBackStack() },
                goNext = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Home) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}