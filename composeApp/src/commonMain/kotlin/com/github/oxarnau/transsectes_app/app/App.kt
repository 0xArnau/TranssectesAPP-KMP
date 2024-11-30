package com.github.oxarnau.transsectes_app.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.github.oxarnau.transsectes_app.app.navigation.NavigationWrapper
import com.github.oxarnau.transsectes_app.core.presentation.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    Theme(isDarkTheme = isSystemInDarkTheme()) {
        NavigationWrapper()
    }
}