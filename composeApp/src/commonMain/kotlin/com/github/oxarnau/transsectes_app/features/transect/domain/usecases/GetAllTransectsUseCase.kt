package com.github.oxarnau.transsectes_app.features.transect.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect
import com.github.oxarnau.transsectes_app.features.transect.domain.repositories.TransectRepository

/**
 * Use case for retrieving all transects.
 *
 * This class is responsible for encapsulating the logic required to fetch all transects
 * from the repository. It represents a single unit of business logic.
 *
 * @property repository The repository responsible for handling transect-related operations.
 */
class GetAllTransectsUseCase(private val repository: TransectRepository) {

    /**
     * Executes the action to retrieve all transects.
     *
     * @return A [Result] containing a list of transects or an error if the operation fails.
     */
    suspend operator fun invoke(): Result<List<Transect>, DataError> {
        return repository.getAllTransects()
    }
}
