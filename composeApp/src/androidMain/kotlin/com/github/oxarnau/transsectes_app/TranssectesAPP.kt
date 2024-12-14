package com.github.oxarnau.transsectes_app

import android.app.Application
import com.github.oxarnau.transsectes_app.di.androidModule
import com.github.oxarnau.transsectes_app.di.initKoin
import org.koin.android.ext.koin.androidContext

class TranssectesAPP : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@TranssectesAPP)
            modules(listOf(androidModule))
        }
    }
}
