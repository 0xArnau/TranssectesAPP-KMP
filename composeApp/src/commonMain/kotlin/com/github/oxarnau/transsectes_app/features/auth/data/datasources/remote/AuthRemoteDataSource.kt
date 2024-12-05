package com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.auth.data.models.UserModel
import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User


/**
 * Remote data source for authentication operations.
 */
interface AuthRemoteDataSource {

    /**
     * Signs in a user with email and password.
     *
     * @param email User's email address.
     * @param password User's password.
     * @return [Result] containing a [User] on success or a [DataError.Remote] on failure.
     */
    suspend fun signIn(
        email: String,
        password: String,
    ): Result<UserModel, DataError>

    /**
     * Registers a new user with email, password, and name.
     *
     * @param email New user's email address.
     * @param password New user's password.
     * @return [Result] containing a [User] on success or a [DataError.Remote] on failure.
     */
    suspend fun signUp(
        email: String,
        password: String,
    ): Result<User, DataError.Remote>

    /**
     * Signs out the current user.
     *
     * @return [Result] indicating success or [DataError.Remote] on failure.
     */
    suspend fun signOut(): Result<Unit, DataError.Remote>

    /**
     * Sends an email verification to the current user.
     *
     * @return [Result] containing true if successful or a [DataError.Remote] on failure.
     */
    suspend fun verifyEmail(): Result<Boolean, DataError.Remote>

    /**
     * Checks if the user is authenticated
     *
     * @return [Result] containing `true` if successful, `false` if user is not
     * authenticated or a [DataError.Remote] on failure.
     */
    suspend fun isUserAutehnticated(): Result<Boolean, DataError.Remote>

    /**
     * Checks if user has verified the email
     *
     * @return [Result] containing `true` if successful, `false` if user has not
     * verified email or a [DataError.Remote] on failure.
     */
    suspend fun isEmailVerified(): Result<Boolean, DataError.Remote>

}