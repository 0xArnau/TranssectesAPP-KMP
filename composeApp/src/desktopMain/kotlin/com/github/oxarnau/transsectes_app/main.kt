package com.github.oxarnau.transsectes_app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.oxarnau.transsectes_app.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TranssectesApp",
    ) {
        App()
    }
}