package com.github.oxarnau.transsectes_app.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home : Route

    @Serializable
    data object Settings : Route
}