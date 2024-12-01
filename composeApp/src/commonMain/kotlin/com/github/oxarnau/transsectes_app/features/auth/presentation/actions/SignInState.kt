package com.github.oxarnau.transsectes_app.features.auth.presentation.actions

/**
 * State class that holds the current state for the Sign In feature.
 *
 * @property isLoading Whether the Sign In process is in progress.
 * @property email The email entered by the user.
 * @property password The password entered by the user.
 * @property errorMessage A message to show in case of an error during the Sign In process.
 */
data class SignInState(
    val isLoading: Boolean = false,
    val email: String? = null,
    val password: String? = null,
    val errorMessage: String? = null,
)
