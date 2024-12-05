package com.github.oxarnau.transsectes_app.features.auth.domain.usecases

import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User
import com.github.oxarnau.transsectes_app.features.auth.domain.repositories.UserRepository

/**
 * Use case for retrieving the user data.
 *
 * This class interacts with the [UserRepository] to get the user data.
 *
 * @param userRepository The repository used to retrieve the user data.
 */
class GetUserUseCase(private val userRepository: UserRepository) {

    /**
     * Executes the use case of retrieving the user data from the repository.
     *
     * @return The user object if available, or null if no user is found.
     */
    suspend operator fun invoke(): User? {
        return userRepository.getUser()
    }
}
