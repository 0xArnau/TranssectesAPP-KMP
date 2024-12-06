package com.github.oxarnau.transsectes_app.core.domain.repositories

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User

/**
 * Interface defining the contract for authentication operations.
 */
interface AuthRepository {

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
    ): Result<User, DataError>

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
     * @return [Result] containing success or a [DataError.Local] on failure.
     */
    suspend fun signOut(): Result<Unit, DataError.Local>

    /**
     * Sends an email verification to the current user.
     *
     * @return [Result] containing true if successful or a [DataError.Remote] on failure.
     */
    suspend fun verifyEmail(): Result<Boolean, DataError.Remote>

    /**
     * Check if user is authenticated and email is verified
     *
     * @return [Result] containing true if user has verified the email, false if is not
     * and [DataError.Remote] on failure
     */
    suspend fun isEmailVerified(): Result<Boolean, DataError.Remote>

    /**
     * Check if user is authenticated and email is verified
     *
     * @return [Result] containing true if user is authenticated,
     * false if is not and [DataError.Remote] on failure
     */
    suspend fun isUserAuthenticated(): Result<Boolean, DataError.Remote>

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return A [Result] containing a [User] object if a user is authenticated,
     * `null` if no user is authenticated, or a [DataError] on failure.
     */
    suspend fun getUserInfo(): Result<User?, DataError>

    /**
     * Checks if the current user is a technician.
     *
     * This function makes a call to the repository to check the user's status.
     * It returns a [Result] that indicates whether the user is a technician or not.
     * In case of a failure, a [DataError.Remote] is returned.
     *
     * @return [Result] containing `true` if the user is a technician, `false` if not,
     *         or a [DataError.Remote] in case of an error.
     */
    suspend fun isTechnician(): Result<Boolean, DataError>

}
