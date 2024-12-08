package com.github.oxarnau.transsectes_app.features.transect.domain.repositories

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect

/**
 * Interface that defines the contract for the Transect repository.
 * This repository will provide methods to interact with transect data,
 * either from a local or remote data source.
 */
interface TransectRepository {

    /**
     * Get a list of transects that were created by a specific user.
     *
     * @param createdBy The user ID or identifier who created the transects.
     * @return A Result object containing a list of Transect entities if successful,
     *         or a DataError if something goes wrong.
     */
    suspend fun getTransectByCurrentUser(): Result<List<Transect>, DataError>

    /**
     * Get all transects available in the system.
     *
     * @return A Result object containing a list of all Transect entities if successful,
     *         or a DataError if something goes wrong.
     */
    suspend fun getAllTransects(): Result<List<Transect>, DataError>

    /**
     * Save a new transect to the data source (e.g., database, remote server).
     *
     * @param transect The Transect entity to be saved.
     * @return A Result object with no content if successful,
     *         or a DataError if the save operation fails.
     */
    suspend fun saveTransect(transect: Transect): Result<Nothing, DataError>
}
