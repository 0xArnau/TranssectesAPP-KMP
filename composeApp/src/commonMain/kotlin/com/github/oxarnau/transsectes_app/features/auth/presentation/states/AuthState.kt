package com.github.oxarnau.transsectes_app.features.auth.presentation.states

/**
 * Represents the state of the authentication screen.
 *
 * @property isLoading Indicates if the authentication process is in progress.
 * @property isUserAuthenticated Indicates whether the user is currently authenticated.
 * @property isUserEmailVerified Indicates whether the user's email has been verified.
 */
data class AuthState(
    val isLoading: Boolean = true,
    val isUserAuthenticated: Boolean = false,
    val isUserEmailVerified: Boolean = false,
)
