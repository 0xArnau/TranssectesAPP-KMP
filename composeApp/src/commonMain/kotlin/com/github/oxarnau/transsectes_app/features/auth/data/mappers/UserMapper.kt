package com.github.oxarnau.transsectes_app.features.auth.data.mappers

import com.github.oxarnau.transsectes_app.features.auth.data.models.UserModel
import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User

class UserMapper {

    fun toEntity(userModel: UserModel): User {
        return User(
            id = userModel.id,
            email = userModel.email,
            isEmailVerified = userModel.isEmailVerified,
            isTechnician = false // TODO
        )
    }
}