package com.github.oxarnau.transsectes_app.features.user.entity

data class User(
    val id: String,
    val email: String,
    val isEmailVerified: Boolean = false,
    val isTechnician: Boolean = false,
)