package com.github.oxarnau.transsectes_app.features.transect.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.repositories.TransectRepository
import com.github.oxarnau.transsectes_app.features.user.repositories.UserRepository

/**
 * Use case for removing all transects from the system.
 *
 * This class encapsulates the business logic for deleting all transects. It includes
 * validation to ensure that only authorized users (e.g., technical users) can perform
 * this operation.
 *
 * @property repository The repository responsible for handling transect-related operations.
 * @property userRepository The repository responsible for managing user-related operations.
 */
class RemoveAllTransectsUseCase(
    private val repository: TransectRepository,
    private val userRepository: UserRepository
) {

    /**
     * Executes the operation to remove all transects.
     *
     * This method first validates that the user has the necessary permissions (e.g., is a technician).
     * If the validation passes, it attempts to delete all transects via the repository.
     *
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend operator fun invoke(): Result<Nothing, DataError> {
        // TODO: Validate if the user is a technician; return an error if not authorized.
        // You might fetch user information from `userRepository` and check their role.

        // TODO: Attempt to remove all transects using the transect repository.
        // Ensure proper error handling and return an appropriate result.
        TODO()
    }
}
