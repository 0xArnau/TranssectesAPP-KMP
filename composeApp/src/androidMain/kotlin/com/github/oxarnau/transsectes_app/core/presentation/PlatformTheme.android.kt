package com.github.oxarnau.transsectes_app.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
actual fun PlatformTheme(
    useDarkTheme: Boolean,
    colorScheme: CustomColorScheme,
    content: @Composable () -> Unit,
) {

    val androidColorScheme = if (useDarkTheme) {
        darkColorScheme(
            primary = Color(colorScheme.primary),
            secondary = Color(colorScheme.secondary),
            tertiary = Color(colorScheme.tertiary)
        )
    } else {
        lightColorScheme(
            primary = Color(colorScheme.primary),
            secondary = Color(colorScheme.secondary),
            tertiary = Color(colorScheme.tertiary)
        )
    }

    MaterialTheme(
        colorScheme = androidColorScheme,
        content = content
    )
}