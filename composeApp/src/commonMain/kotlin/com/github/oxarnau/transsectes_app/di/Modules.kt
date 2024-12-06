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
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.GetUserInfoUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.GetUserUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsEmailVerifiedUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsUserAuthenticatedUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsUserTechnicianUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.SaveUserUseCase
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.AuthViewModel
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.SignInViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {

    // Data Sources
    singleOf(::AuthRemoteDataSourceImpl).bind<AuthRemoteDataSource>()
    singleOf(::UserLocalDataSourceImpl).bind<UserLocalDataSource>()

    // Repositories
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    // Use Cases
    singleOf(::GetUserUseCase)
    singleOf(::GetUserInfoUseCase)
    singleOf(::SaveUserUseCase)
    singleOf(::IsEmailVerifiedUseCase)
    singleOf(::IsUserAuthenticatedUseCase)
    singleOf(::SignInUseCase)
    singleOf(::IsUserTechnicianUseCase)

    // View Models
    viewModelOf(::AuthViewModel)
    viewModelOf(::SignInViewModel)
}
