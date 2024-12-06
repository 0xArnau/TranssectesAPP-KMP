package com.github.oxarnau.transsectes_app.shared.domain.entity

data class User(
    val id: String,
    val email: String,
    val isEmailVerified: Boolean = false,
    val isTechnician: Boolean = false,
)