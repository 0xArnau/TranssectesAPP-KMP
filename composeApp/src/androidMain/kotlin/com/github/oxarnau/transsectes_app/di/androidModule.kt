package com.github.oxarnau.transsectes_app.di

import com.github.oxarnau.transsectes_app.core.data.datasources.FileManagerDataSourceImpl
import com.github.oxarnau.transsectes_app.core.data.repositories.FileManagerRepositoryImpl
import com.github.oxarnau.transsectes_app.core.domain.datasources.FileManagerDataSource
import com.github.oxarnau.transsectes_app.core.domain.repositories.FileManagerRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule = module {

    // Data Sources
    singleOf(::FileManagerDataSourceImpl).bind<FileManagerDataSource>()

    // Repositories
    singleOf(::FileManagerRepositoryImpl).bind<FileManagerRepository>()
}