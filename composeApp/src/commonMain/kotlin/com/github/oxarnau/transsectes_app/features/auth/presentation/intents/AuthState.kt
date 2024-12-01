package com.github.oxarnau.transsectes_app.features.auth.presentation.intents

data class AuthState(
    val isLoading: Boolean = true,
    val isUserEmailVerified: Boolean = false,
)
