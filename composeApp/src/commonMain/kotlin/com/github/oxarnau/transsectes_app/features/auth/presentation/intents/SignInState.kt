package com.github.oxarnau.transsectes_app.features.auth.presentation.intents

data class SignInState(
    val isLoading: Boolean = true,
    val isUserEmailVerified: Boolean = false,
)
