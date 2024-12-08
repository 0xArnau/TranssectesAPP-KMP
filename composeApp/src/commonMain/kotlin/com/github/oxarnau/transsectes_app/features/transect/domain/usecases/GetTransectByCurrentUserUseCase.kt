package com.github.oxarnau.transsectes_app.features.transect.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect
import com.github.oxarnau.transsectes_app.features.transect.domain.repositories.TransectRepository

/**
 * Use case for retrieving transects created by a specific user.
 *
 * This use case encapsulates the logic needed to fetch transects based on the creator's identifier.
 * It abstracts the business logic and interacts with the repository for data fetching.
 *
 * @property repository The repository responsible for handling transect-related operations.
 */
class GetTransectByCurrentUserUseCase(private val repository: TransectRepository) {

    /**
     * Executes the action to fetch transects created by a specific user.
     *
     * @param createdBy The identifier of the user who created the transects.
     * @return A [Result] containing a list of transects or an error if the operation fails.
     */
    suspend operator fun invoke(): Result<List<Transect>, DataError> {
        return repository.getTransectByCurrentUser()
    }
}
