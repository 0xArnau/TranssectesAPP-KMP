package com.github.oxarnau.transsectes_app.features.auth.presentation.intents

/**
 * Represents the possible user intents for the authentication screen.
 */
sealed interface AuthIntent {

    /** Intent triggered when the user clicks the "Sign In" button. */
    data object onSignInClick : AuthIntent

    /** Intent triggered when the user clicks the "Sign Up" button. */
    data object onSignUpClick : AuthIntent
}
