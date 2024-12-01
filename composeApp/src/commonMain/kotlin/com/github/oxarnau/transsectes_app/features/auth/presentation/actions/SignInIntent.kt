package com.github.oxarnau.transsectes_app.features.auth.presentation.actions

sealed interface SignInIntent {

    data object onForgotPassword : SignInIntent

    data object onBackClick : SignInIntent

    data object onNextClick : SignInIntent

}
