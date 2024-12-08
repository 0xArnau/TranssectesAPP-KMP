package com.github.oxarnau.transsectes_app.di

import com.github.oxarnau.transsectes_app.core.domain.repositories.AuthRepository
import com.github.oxarnau.transsectes_app.core.domain.usecases.SignInUseCase
import com.github.oxarnau.transsectes_app.features.auth.data.datasources.remote.AuthFirebaseDataSourceImpl
import com.github.oxarnau.transsectes_app.features.auth.data.repositoiries.AuthRepositoryImpl
import com.github.oxarnau.transsectes_app.features.auth.domain.datasources.AuthDataSource
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.GetUserInfoUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsEmailVerifiedUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsUserAuthenticatedUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.IsUserTechnicianUseCase
import com.github.oxarnau.transsectes_app.features.auth.domain.usecases.SignOutUseCase
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.AuthViewModel
import com.github.oxarnau.transsectes_app.features.auth.presentation.viewmodels.SignInViewModel
import com.github.oxarnau.transsectes_app.features.settings.presentation.viewmodels.SettingsViewModel
import com.github.oxarnau.transsectes_app.features.transect.data.datasources.remote.TransectFirebaseDataSourceImpl
import com.github.oxarnau.transsectes_app.features.transect.data.repositories.TransectRepositoryImpl
import com.github.oxarnau.transsectes_app.features.transect.domain.datasources.TransectDataSource
import com.github.oxarnau.transsectes_app.features.transect.domain.repositories.TransectRepository
import com.github.oxarnau.transsectes_app.features.transect.domain.usecases.GetAllTransectsUseCase
import com.github.oxarnau.transsectes_app.features.transect.domain.usecases.GetTransectByCreatedByUseCase
import com.github.oxarnau.transsectes_app.features.transect.domain.usecases.RemoveAllTransectsUseCase
import com.github.oxarnau.transsectes_app.features.transect.domain.usecases.SaveTransectUseCase
import com.github.oxarnau.transsectes_app.features.transect.presentation.records.viewmodels.RecordsViewModel
import com.github.oxarnau.transsectes_app.features.user.data.datasources.local.UserLocalDataSource
import com.github.oxarnau.transsectes_app.features.user.data.datasources.local.memory.UserLocalDataSourceImpl
import com.github.oxarnau.transsectes_app.features.user.data.repositories.UserRepositoryImpl
import com.github.oxarnau.transsectes_app.features.user.domain.repositories.UserRepository
import com.github.oxarnau.transsectes_app.features.user.domain.usecases.GetUserUseCase
import com.github.oxarnau.transsectes_app.features.user.domain.usecases.SaveUserUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val sharedModule = module {

    // Data Sources
    singleOf(::AuthFirebaseDataSourceImpl).bind<AuthDataSource>()
    singleOf(::UserLocalDataSourceImpl).bind<UserLocalDataSource>()
    singleOf(::TransectFirebaseDataSourceImpl).bind<TransectDataSource>()

    // Repositories
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::TransectRepositoryImpl).bind<TransectRepository>()

    // Use Cases
    singleOf(::GetUserUseCase)
    singleOf(::GetUserInfoUseCase)
    singleOf(::SaveUserUseCase)
    singleOf(::IsEmailVerifiedUseCase)
    singleOf(::IsUserAuthenticatedUseCase)
    singleOf(::SignInUseCase)
    singleOf(::IsUserTechnicianUseCase)
    singleOf(::SignOutUseCase)
    singleOf(::GetAllTransectsUseCase)
    singleOf(::GetTransectByCreatedByUseCase)
    singleOf(::SaveTransectUseCase)
    singleOf(::RemoveAllTransectsUseCase)

    // View Models
    viewModelOf(::AuthViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::RecordsViewModel)
}
