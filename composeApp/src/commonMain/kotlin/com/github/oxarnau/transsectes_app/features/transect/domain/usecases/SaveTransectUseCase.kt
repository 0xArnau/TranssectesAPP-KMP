package com.github.oxarnau.transsectes_app.features.transect.domain.usecases

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect
import com.github.oxarnau.transsectes_app.features.transect.domain.repositories.TransectRepository

/**
 * Use case for saving a Transect.
 *
 * This class represents a specific business action for saving a transect. It interacts
 * with the repository to delegate the data-saving process.
 *
 * @property repository The repository responsible for handling transect-related operations.
 */
class SaveTransectUseCase(private val repository: TransectRepository) {

    /**
     * Executes the save action for a given transect.
     *
     * @param transect The transect entity to save.
     * @return A [Result] indicating the success or failure of the operation.
     */
    suspend operator fun invoke(transect: Transect): Result<Nothing, DataError> {
        return repository.saveTransect(transect)
    }
}
