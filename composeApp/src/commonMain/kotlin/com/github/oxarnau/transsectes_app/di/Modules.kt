package com.github.oxarnau.transsectes_app.di

import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.core.domain.usecases.SignInUseCase
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.local.UserLocalDataSource
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.local.memory.UserLocalDataSourceImpl
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote.firebase.AuthRemoteDataSourceImpl
import com.github.oxarnau.transsectes_app.features.auth.data.repositoiries.AuthRepositoryImpl
import com.github.oxarnau.transsectes_app.features.auth.data.repositories.UserRepositoryImpl
import com.github.oxarnau.transsectes_app.features.auth.domain.repositories.UserRepository
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.GetUserUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsEmailVerifiedUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsUserAuthenticatedUseCase
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.AuthViewModel
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.SignInViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val sharedModule = module {

    // Data Sources
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl() }
    single<UserLocalDataSource> { UserLocalDataSourceImpl() }

    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(authRemoteDataSource = get()) }
    single<UserRepository> { UserRepositoryImpl(userLocalDataSource = get()) }

    // Use Cases
    single { GetUserUseCase(userRepository = get()) }
    single { IsEmailVerifiedUseCase(authRepository = get()) }
    single { IsUserAuthenticatedUseCase(authRepository = get()) }
    single { SignInUseCase(authRepository = get()) }

    // View Models
    viewModelOf(::AuthViewModel)
    viewModelOf(::SignInViewModel)
}
