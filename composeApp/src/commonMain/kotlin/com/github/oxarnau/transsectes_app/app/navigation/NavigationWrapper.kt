package com.github.oxarnau.transsectes_app.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.oxarnau.transsectes_app.features.auth.presentation.views.AuthView
import com.github.oxarnau.transsectes_app.features.auth.presentation.views.SignInView
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
import com.github.oxarnau.transsectes_app.features.splash.presentation.view.SplashView
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.RecordsTransectsView
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.views.TransectsDetailView
import org.koin.compose.viewmodel.koinViewModel

expect fun log(tag: String, message: String)

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    val recordsViewModel: RecordsViewModel = koinViewModel()

    navController.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller
            .currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")

        println("NavController: NavigationWrapper BackStack: $routes")
    }

    NavHost(
        navController = navController,
        startDestination = Route.Splash
    ) {
        composable<Route.Splash> {
            log("NavController", "Route.Splash")
            SplashView {
                navController.navigate(Route.Auth) {
                    popUpTo(Route.Splash) { inclusive = true }
                }
            }
        }

        composable<Route.Auth> {
            AuthView(navController)
        }

        composable<Route.SignIn> {
            SignInView(navController)
        }

        composable<Route.Home> {
            HomeView(
                navigate2HowTo = { navController.navigate(Route.HowTo1) },
                navigate2Records = { navController.navigate(Route.RecordsTransects) },
                navigate2Contact = { navController.navigate(Route.Contact) }
            ) { navController.navigate(Route.Settings) }
        }

        composable<Route.Settings> {
            SettingsView(navController)
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

        composable<Route.RecordsTransects> {
            RecordsTransectsView(navController, recordsViewModel)
        }

        composable<Route.DetailedTransect> {
            TransectsDetailView(navController, recordsViewModel)
        }
    }
}
