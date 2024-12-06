package com.github.oxarnau.transsectes_app.features.auth.data.repositoiries

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.map
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.github.oxarnau.transsectes_app.features.auth.data.mappers.UserMapper
import com.github.oxarnau.transsectes_app.features.user.entity.User

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String,
    ): Result<User, DataError> {
        return authRemoteDataSource.signIn(email, password).map {
            UserMapper().toEntity(it)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
    ): Result<User, DataError.Remote> {
        return authRemoteDataSource.signUp(email, password)
    }

    override suspend fun signOut(): Result<Unit, DataError.Remote> {
        return authRemoteDataSource.signOut()
    }

    override suspend fun verifyEmail(): Result<Boolean, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun isEmailVerified(): Result<Boolean, DataError.Remote> {
        return authRemoteDataSource.isEmailVerified()
    }

    override suspend fun isUserAuthenticated(): Result<Boolean, DataError.Remote> {
        return authRemoteDataSource.isUserAutehnticated()
    }

    override suspend fun getUserInfo(): Result<User?, DataError> {
        return authRemoteDataSource.getUserInfo()
    }

    override suspend fun isTechnician(): Result<Boolean, DataError> {
        return authRemoteDataSource.isTechnician()
    }


}
