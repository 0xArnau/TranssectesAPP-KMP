package com.github.oxarnau.transsectes_app.features.transect.domain.datasources

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.transect.domain.entities.Transect

/**
 * Interface that defines the contract for the Transect data source.
 * A data source is where data comes from (e.g., local database, remote server, etc.).
 * This interface describes the operations that can be performed on transect data,
 * whether it's fetching or saving transect entities.
 */
interface TransectDataSource {

    /**
     * Get a list of transects that were created by a specific user.
     *
     * @param createdBy The user ID or identifier who created the transects.
     * @return A Result object containing a list of Transect entities if successful,
     *         or a DataError if something goes wrong.
     */
    suspend fun getTransectByCreatedBy(createBy: String): Result<List<Transect>, DataError>

    /**
     * Get all transects available in the data source (could be a local database or remote server).
     *
     * @return A Result object containing a list of all Transect entities if successful,
     *         or a DataError if something goes wrong.
     */
    suspend fun getAllTransects(): Result<List<Transect>, DataError>

    /**
     * Save a new transect entity to the data source (e.g., local database or remote server).
     *
     * @param transect The Transect entity to be saved.
     * @return A Result object with no content if successful,
     *         or a DataError if the save operation fails.
     */
    suspend fun saveTransect(transect: Transect): Result<Nothing, DataError>
}
