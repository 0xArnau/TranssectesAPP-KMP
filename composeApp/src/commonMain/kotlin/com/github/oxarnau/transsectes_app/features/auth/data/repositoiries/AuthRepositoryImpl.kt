package com.github.oxarnau.transsectes_app.features.auth.data.repositoiries

import com.github.oxarnau.transsectes_app.core.domain.DataError
import com.github.oxarnau.transsectes_app.core.domain.Result
import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.github.oxarnau.transsectes_app.features.auth.domain.entity.User

class AuthRepositoryImpl(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun signIn(
        email: String,
        password: String,
    ): Result<User, DataError.Remote> {
        return authRemoteDataSource.signIn(email, password)
    }

    override suspend fun signUp(
        email: String,
        password: String,
    ): Result<User, DataError.Remote> {
        return authRemoteDataSource.signUp(email, password)
    }

    override suspend fun signOut(): Result<Unit, DataError.Local> {
        TODO("Not yet implemented")
    }

    override suspend fun verifyEmail(): Result<Boolean, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun isEmailVerified(): Result<Boolean, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun isUserAuthenticated(): Result<Boolean, DataError.Remote> {
        TODO("Not yet implemented")
    }
}
