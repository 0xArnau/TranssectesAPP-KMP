package com.github.oxarnau.transsectes_app.features.auth.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository

class IsEmailVerifiedUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Result<Boolean, DataError.Remote> {
        return authRepository.isEmailVerified()
    }
}
