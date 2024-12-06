package com.github.oxarnau.transsectes_app.features.auth.domain.repositories

import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User

/**
 * Interface for managing user data.
 *
 * This interface defines the methods for interacting with user data, abstracting away
 * the underlying data sources (local, remote, etc.).
 */
interface UserRepository {

    /**
     * Saves the user data.
     *
     * @param user The user object to be saved.
     */
    suspend fun saveUser(user: User?)

    /**
     * Retrieves the stored user data.
     *
     * @return The user object if available, or null if no user is stored.
     */
    suspend fun getUser(): User?

    /**
     * Clears the stored user data.
     */
    suspend fun clearUser()
}
