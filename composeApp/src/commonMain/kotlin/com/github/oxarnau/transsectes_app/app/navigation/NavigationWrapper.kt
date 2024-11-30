package com.github.oxarnau.transsectes_app.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.oxarnau.transsectes_app.features.contact.presentation.views.ContactView
import com.github.oxarnau.transsectes_app.features.home.presentation.views.HomeView
import com.github.oxarnau.transsectes_app.features.howto.presentation.views.HowTo1
import com.github.oxarnau.transsectes_app.features.howto.presentation.views.HowTo2
import com.github.oxarnau.transsectes_app.features.howto.presentation.views.HowTo3
import com.github.oxarnau.transsectes_app.features.howto.presentation.views.HowTo4
import com.github.oxarnau.transsectes_app.features.howto.presentation.views.HowTo5
import com.github.oxarnau.transsectes_app.features.howto.presentation.views.HowTo6
import com.github.oxarnau.transsectes_app.features.howto.presentation.views.HowTo7
import com.github.oxarnau.transsectes_app.features.settings.presentation.views.SettingsView

expect fun log(tag: String, message: String)

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()


    navController.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller
            .currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")

        log("NavController: BackStackLog", "BackStack: $routes")
    }

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