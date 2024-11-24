package com.github.oxarnau.transsectes_app.core.presentation

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformTheme(
    useDarkTheme: Boolean,
    colorScheme: CustomColorScheme,
    content: @Composable () -> Unit,
) {

    content()
}