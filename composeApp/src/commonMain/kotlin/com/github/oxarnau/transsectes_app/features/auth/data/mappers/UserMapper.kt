package com.github.oxarnau.transsectes_app.features.auth.data.mappers

import com.github.oxarnau.transsectes_app.features.auth.data.models.UserModel
import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User
import dev.gitlive.firebase.auth.FirebaseUser

/**
 * Mapper class for converting data between different user-related models.
 *
 * This class provides methods to convert data from remote and local sources into
 * domain entities that can be used in the application logic.
 */
class UserMapper {

    /**
     * Converts a [UserModel] to a domain [User] entity.
     *
     * @param userModel The [UserModel] from the data layer.
     * @return The corresponding [User] domain entity.
     */
    fun toEntity(userModel: UserModel): User {
        return User(
            id = userModel.id,
            email = userModel.email,
            isEmailVerified = userModel.isEmailVerified,
            isTechnician = false // TODO: Determine if the user is a technician from additional data.
        )
    }

    /**
     * Converts a [FirebaseUser] to a domain [User] entity.
     *
     * @param userFirebaseUser The [FirebaseUser] from the Firebase SDK.
     * @return The corresponding [User] domain entity, or null if the input is null.
     */
    fun toEntity(userFirebaseUser: FirebaseUser?): User? {
        if (userFirebaseUser?.email == null) return null

        return User(
            id = userFirebaseUser.uid,
            email = userFirebaseUser.email!!,
            isEmailVerified = userFirebaseUser.isEmailVerified,
            isTechnician = false // TODO: Determine if the user is a technician from additional data.
        )
    }
}
