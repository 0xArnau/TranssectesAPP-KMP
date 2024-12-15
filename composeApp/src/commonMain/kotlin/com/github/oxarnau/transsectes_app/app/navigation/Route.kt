package com.github.oxarnau.transsectes_app.app.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object GoBack : Route

    @Serializable
    data object Splash : Route

    @Serializable
    data object Auth : Route

    @Serializable
    data object SignIn : Route

    @Serializable
    data object ForgotPassword : Route

    @Serializable
    data object VerifyEmail : Route

    @Serializable
    data object SignUp : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data object Contact : Route

    @Serializable
    data object HowTo1 : Route

    @Serializable
    data object HowTo2 : Route

    @Serializable
    data object HowTo3 : Route

    @Serializable
    data object HowTo4 : Route

    @Serializable
    data object HowTo5 : Route

    @Serializable
    data object HowTo6 : Route

    @Serializable
    data object HowTo7 : Route

    @Serializable
    data object HowTo8 : Route

    @Serializable
    data object AllTransects : Route

    @Serializable
    data object DonwloadTransects : Route

    @Serializable
    data object MyTransects : Route

    @Serializable
    data object RecordsTransects : Route

    @Serializable
    data object RemoveTransects : Route

    @Serializable
    data object DetailedTransect : Route

    @Serializable
    data object StartTransect : Route
}
