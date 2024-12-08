package com.github.oxarnau.transsectes_app.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(listOf(sharedModule, firebaseModule))
    }
}
