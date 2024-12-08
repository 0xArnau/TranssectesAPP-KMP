package com.github.oxarnau.transsectes_app.features.auth.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.features.user.domain.entity.User

/**
 * Use case for retrieving information about the currently authenticated user.
 *
 * This use case encapsulates the logic for interacting with the [AuthRepository]
 * to fetch user details, abstracting the direct dependency on the repository
 * from the presentation or other layers of the application.
 *
 * @property repository An instance of [AuthRepository] to perform user-related operations.
 */
class GetUserInfoUseCase(private val repository: AuthRepository) {

    /**
     * Executes the use case to retrieve the authenticated user's information.
     *
     * @return A [Result] containing a [User] object if the user is authenticated,
     * `null` if no user is authenticated, or a [DataError] if an error occurs.
     */
    suspend operator fun invoke(): Result<User?, DataError> {
        return repository.getUserInfo()
    }
}
