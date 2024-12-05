package com.github.oxarnau.transsectes_app.di

import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.local.UserLocalDataSource
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.local.memory.UserLocalDataSourceImpl
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote.AuthRemoteDataSource
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote.firebase.AuthRemoteDataSourceImpl
import com.github.oxarnau.transsectes_app.features.auth.data.repositoiries.AuthRepositoryImpl
import com.github.oxarnau.transsectes_app.features.auth.data.repositories.UserRepositoryImpl
import com.github.oxarnau.transsectes_app.features.auth.domain.repositories.UserRepository
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.AuthViewModel
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.SignInViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {

    singleOf(::AuthRemoteDataSourceImpl).bind<AuthRemoteDataSource>()
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::UserLocalDataSourceImpl).bind<UserLocalDataSource>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()

    // TODO: añadir en los ViewModels necesarios userRepository
    viewModel { SignInViewModel(authRepository = get()) }
    viewModel { AuthViewModel(authRepository = get()) }
}
