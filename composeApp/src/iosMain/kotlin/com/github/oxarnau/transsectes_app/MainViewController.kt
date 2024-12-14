package com.github.oxarnau.transsectes_app

import androidx.compose.ui.window.ComposeUIViewController
import com.github.oxarnau.transsectes_app.app.App
import com.github.oxarnau.transsectes_app.di.initKoin
import com.github.oxarnau.transsectes_app.di.iosModule

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin {
            modules(listOf(iosModule))
        }
    }
) { App() }
