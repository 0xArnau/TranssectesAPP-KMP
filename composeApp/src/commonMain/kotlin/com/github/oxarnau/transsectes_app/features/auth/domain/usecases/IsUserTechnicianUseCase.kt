package com.github.oxarnau.transsectes_app.features.auth.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository

/**
 * Use case to check if the current user is a technician.
 * It acts as a business logic layer between the repository and the caller,
 * abstracting the implementation details of the repository.
 *
 * @property repository The [AuthRepository] to perform the check.
 */
class IsUserTechnicianUseCase(private val repository: AuthRepository) {

    /**
     * Executes the use case to determine if the user is a technician.
     *
     * @return [Result] containing `true` if the user is a technician, `false` if not,
     * or a [DataError] if the operation fails.
     */
    suspend operator fun invoke(): Result<Boolean, DataError> {
        // Delegates the call to the repository to check technician status
        return repository.isTechnician()
    }
}
