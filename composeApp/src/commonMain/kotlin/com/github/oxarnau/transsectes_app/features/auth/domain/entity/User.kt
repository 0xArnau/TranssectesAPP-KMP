package com.github.oxarnau.transsectes_app.features.auth.domain.entity

class User(
    val id: String,
    val email: String,
    val isEmailVerified: Boolean = false,
    val isTechnician: Boolean = false,
)