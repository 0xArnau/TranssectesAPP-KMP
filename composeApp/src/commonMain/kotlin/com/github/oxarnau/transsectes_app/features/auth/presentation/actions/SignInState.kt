package com.github.oxarnau.transsectes_app.features.auth.presentation.actions

data class SignInState(
    val isLoading: Boolean = false,
    val email: String? = null,
    val password: String? = null,
)
