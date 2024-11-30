package com.github.oxarnau.transsectes_app.features.auth.presentation.actions

sealed interface SignInIntent {

    data class onSignInClick(val email: String, val password: String) :
        SignInIntent

    data object onForgotPassword : SignInIntent

    data object onSignUpClick : SignInIntent
}
