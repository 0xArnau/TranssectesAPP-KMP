package com.github.oxarnau.transsectes_app.features.auth.presentation.intents

/**
 * Intent sealed class to represent different actions a user can take in the Sign In feature.
 */
sealed interface SignInIntent {

    /**
     * Triggered when the user clicks the "Forgot Password" button.
     */
    data object onForgotPassword : SignInIntent

    /**
     * Triggered when the user clicks the "Back" button.
     */
    data object onBackClick : SignInIntent

    /**
     * Triggered when the user clicks the "Next" button to submit the form.
     */
    data object onNextClick : SignInIntent

    /**
     * Triggered when the user modifies the email field.
     *
     * @property email The new email entered by the user.
     */
    data class onEmailChange(val email: String) : SignInIntent

    /**
     * Triggered when the user modifies the password field.
     *
     * @property password The new password entered by the user.
     */
    data class onPasswordChange(val password: String) : SignInIntent
}

