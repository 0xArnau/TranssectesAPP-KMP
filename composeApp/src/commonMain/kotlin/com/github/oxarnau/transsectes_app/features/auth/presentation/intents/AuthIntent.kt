package com.github.oxarnau.transsectes_app.features.auth.presentation.intents

sealed interface AuthIntent {

    data class onSignInClick(val email: String, val password: String) :
        AuthIntent

    data object onSignUpClick : AuthIntent
}
