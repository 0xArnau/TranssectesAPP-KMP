package com.github.oxarnau.transsectes_app.shared.data.repositories

import com.github.oxarnau.transsectes_app.features.user.entity.User
import com.github.oxarnau.transsectes_app.features.user.repositories.UserRepository
import com.github.oxarnau.transsectes_app.shared.data.datasources.local.UserLocalDataSource

/**
 * Implementation of the [UserRepository] interface.
 *
 * This class interacts with the [UserLocalDataSource] to manage user data.
 */
class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    /**
     * Saves the user data using the local data source.
     *
     * @param user The user object to be saved.
     */
    override suspend fun saveUser(user: User?) {
        userLocalDataSource.saveUser(user)
    }

    /**
     * Retrieves the stored user data from the local data source.
     *
     * @return The user object if available, or null if no user is found.
     */
    override suspend fun getUser(): User? {
        return userLocalDataSource.getUser()
    }

    /**
     * Clears the stored user data from the local data source.
     */
    override suspend fun clearUser() {
        userLocalDataSource.clearUser()
    }
}
