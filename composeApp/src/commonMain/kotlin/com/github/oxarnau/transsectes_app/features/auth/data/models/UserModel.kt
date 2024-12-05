package com.github.oxarnau.transsectes_app.features.auth.data.models

data class UserModel(
    val id: String,
    val email: String,
    val isEmailVerified: Boolean = false,
)