package com.github.oxarnau.transsectes_app.features.auth.data.models

data class UserModel(
    private val id: String,
    private val email: String,
    private val isEmailVerified: Boolean,
)