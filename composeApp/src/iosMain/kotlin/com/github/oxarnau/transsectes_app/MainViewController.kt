package com.github.oxarnau.transsectes_app

import androidx.compose.ui.window.ComposeUIViewController
import com.github.oxarnau.transsectes_app.app.App
import com.github.oxarnau.transsectes_app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }
