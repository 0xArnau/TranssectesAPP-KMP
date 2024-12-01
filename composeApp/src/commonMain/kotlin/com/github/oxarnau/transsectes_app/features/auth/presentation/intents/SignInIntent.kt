package com.github.oxarnau.transsectes_app.features.auth.presentation.intents

sealed interface SignInIntent {

    data object onForgotPassword : SignInIntent

    data object onBackClick : SignInIntent

    data object onNextClick : SignInIntent

}
