package com.github.oxarnau.transsectes_app.shared.data.datasources.local.memory

import com.github.oxarnau.transsectes_app.shared.data.datasources.local.UserLocalDataSource
import com.github.oxarnau.transsectes_app.shared.domain.entity.User

/**
 * Implementation of the [UserLocalDataSource] interface that stores user data in memory.
 *
 * This class holds the user data in a private variable and provides methods to save, retrieve,
 * and clear the user data in memory.
 */
class UserLocalDataSourceImpl : UserLocalDataSource {

    // Private variable to store the current user in memory
    private var currentUser: User? = null

    /**
     * Saves the provided user in memory.
     *
     * @param user The user object to be saved in memory.
     */
    override fun saveUser(user: User?) {
        currentUser = user
    }

    /**
     * Retrieves the stored user from memory.
     *
     * @return The user object if it is available, or null if there is no user stored.
     */
    override fun getUser(): User? {
        return currentUser
    }

    /**
     * Clears the stored user from memory.
     */
    override fun clearUser() {
        currentUser = null
    }
}
