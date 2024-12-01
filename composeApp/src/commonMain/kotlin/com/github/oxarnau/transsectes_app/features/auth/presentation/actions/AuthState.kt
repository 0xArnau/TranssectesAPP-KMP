package com.github.oxarnau.transsectes_app.features.auth.presentation.actions

data class AuthState(
    val isLoading: Boolean = true,
    val isUserEmailVerified: Boolean = false,
)
