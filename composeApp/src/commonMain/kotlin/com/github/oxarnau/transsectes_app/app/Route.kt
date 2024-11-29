package com.github.oxarnau.transsectes_app.app

import kotlinx.serialization.Serializable

sealed interface Route {

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
}