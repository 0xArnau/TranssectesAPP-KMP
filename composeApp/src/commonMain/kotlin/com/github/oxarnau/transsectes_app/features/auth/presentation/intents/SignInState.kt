package com.github.oxarnau.transsectes_app.features.auth.presentation.intents

data class SignInState(
    val isLoading: Boolean = false,
    val email: String? = null,
    val password: String? = null,
)
