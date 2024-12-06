package com.github.oxarnau.transsectes_app.features.settings.presentation.states

/**
 * Represents the state of the Settings screen.
 *
 * @property isLoading Indicates whether the screen is in a loading state.
 * @property email The email address of the current user.
 * @property isTechnician Whether the user has technician privileges.
 * @property errorMessage Optional error message to display in case of errors.
 */
data class SettingsState(
    val isLoading: Boolean = false,
    val email: String? = null,
    val isTechnician: Boolean = false,
    val errorMessage: String? = null,
)
