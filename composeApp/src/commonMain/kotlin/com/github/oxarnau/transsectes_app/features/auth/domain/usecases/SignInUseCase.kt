package com.github.oxarnau.transsectes_app.core.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User

/**
 * Use case for signing in a user.
 *
 * @property authRepository Repository to perform authentication operations.
 */
class SignInUseCase(private val authRepository: AuthRepository) {

    /**
     * Executes the sign-in operation.
     *
     * @param email User's email address.
     * @param password User's password.
     * @return [Result] containing a [User] on success or a [DataError.Remote] on failure.
     */
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<User, DataError> {
        return authRepository.signIn(email, password)
    }
}
