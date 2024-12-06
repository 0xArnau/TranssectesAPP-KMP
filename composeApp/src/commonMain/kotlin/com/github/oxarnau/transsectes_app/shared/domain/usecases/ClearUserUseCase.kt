package com.github.oxarnau.transsectes_app.shared.domain.usecases

import com.github.oxarnau.transsectes_app.shared.domain.repositories.UserRepository

/**
 * Use case for clearing the user data.
 *
 * This class interacts with the [UserRepository] to clear the stored user data.
 *
 * @param userRepository The repository used to clear the user data.
 */
class ClearUserUseCase(private val userRepository: UserRepository) {

    /**
     * Executes the use case of clearing the user data in the repository.
     */
    suspend operator fun invoke() {
        userRepository.clearUser()
    }
}
