package com.github.oxarnau.transsectes_app.features.auth.domain.usecases

import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User
import com.github.oxarnau.transsectes_app.features.auth.domain.repositories.UserRepository

/**
 * Use case for saving the user data.
 *
 * This class interacts with the [UserRepository] to save the user data.
 *
 * @param userRepository The repository used to save the user data.
 */
class SaveUserUseCase(private val userRepository: UserRepository) {

    /**
     * Executes the use case of saving the user data in the repository.
     *
     * @param user The user object to be saved.
     */
    suspend operator fun invoke(user: User?) {
        // TODO: remove
        println(
            "id: ${user?.id}, email: ${user?.email}, isEmailVerified: ${user?.isEmailVerified}, " +
                    "isTechnician: ${user?.isTechnician},"
        )
        userRepository.saveUser(user)
    }
}
