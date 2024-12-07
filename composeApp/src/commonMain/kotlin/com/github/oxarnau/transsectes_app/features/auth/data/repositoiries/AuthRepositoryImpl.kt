package com.github.oxarnau.transsectes_app.features.auth.data.repositoiries

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.map
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.features.auth.data.mappers.UserMapper
import com.github.oxarnau.transsectes_app.features.auth.domain.datasources.AuthDataSource
import com.github.oxarnau.transsectes_app.features.user.entity.User

class AuthRepositoryImpl(
    private val datasource: AuthDataSource,
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String,
    ): Result<User, DataError> {
        return datasource.signIn(email, password).map {
            UserMapper().toEntity(it)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
    ): Result<User, DataError.Remote> {
        return datasource.signUp(email, password)
    }

    override suspend fun signOut(): Result<Unit, DataError.Remote> {
        return datasource.signOut()
    }

    override suspend fun verifyEmail(): Result<Boolean, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun isEmailVerified(): Result<Boolean, DataError.Remote> {
        return datasource.isEmailVerified()
    }

    override suspend fun isUserAuthenticated(): Result<Boolean, DataError.Remote> {
        return datasource.isUserAutehnticated()
    }

    override suspend fun getUserInfo(): Result<User?, DataError> {
        return datasource.getUserInfo()
    }

    override suspend fun isTechnician(): Result<Boolean, DataError> {
        return datasource.isTechnician()
    }


}
