package com.github.oxarnau.transsectes_app.features.auth.data.datasources.local

import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User

/**
 * Interface for managing user data locally.
 *
 * This interface defines the methods for saving, retrieving, and clearing user data in the local data source.
 */
interface UserLocalDataSource {

    /**
     * Saves the user locally.
     *
     * @param user The user object to be saved locally.
     */
    fun saveUser(user: User?)

    /**
     * Retrieves the stored user from the local data source.
     *
     * @return The user object if available, or null if no user is stored.
     */
    fun getUser(): User?

    /**
     * Clears the stored user from the local data source.
     */
    fun clearUser()
}
