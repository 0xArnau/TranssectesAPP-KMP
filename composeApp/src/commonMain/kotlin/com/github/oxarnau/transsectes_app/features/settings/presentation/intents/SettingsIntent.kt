package com.github.oxarnau.transsectes_app.features.settings.presentation.intents

/**
 * Represents user intents (actions) for the Settings screen.
 */
sealed interface SettingsIntent {

    /**
     * Represents the user's intent to navigate back from the Settings screen.
     */
    data object GoBackClick : SettingsIntent

    /**
     * Represents the user's intent to sign out of the application.
     */
    data object SignOutClick : SettingsIntent

    /**
     * Represents the user's intent to delete their account.
     */
    data object DeleteAccountClick : SettingsIntent
}
