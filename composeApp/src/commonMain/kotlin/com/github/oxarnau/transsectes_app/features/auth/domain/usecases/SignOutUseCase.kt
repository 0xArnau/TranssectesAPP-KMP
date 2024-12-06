package com.github.oxarnau.transsectes_app.features.auth.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository

/**
 * A use case that handles the sign-out process.
 *
 * This use case invokes the [AuthRepository] to perform the sign-out operation and returns the result.
 * It is typically used when the user requests to log out of the application.
 *
 * @param repository The [AuthRepository] responsible for managing authentication-related operations.
 */
class SignOutUseCase(private val repository: AuthRepository) {

    /**
     * Executes the sign-out operation.
     *
     * This function calls the [signOut] method on the [AuthRepository] and returns a [Result].
     * It returns a [Result] of type [Unit] on success, or a [DataError.Remote] error on failure.
     *
     * @return A [Result] containing either a successful sign-out operation (of type [Unit])
     *         or a [DataError.Remote] in case of an error.
     */
    suspend operator fun invoke(): Result<Unit, DataError.Remote> {
        return repository.signOut()
    }
}
