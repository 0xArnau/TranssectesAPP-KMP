package com.github.oxarnau.transsectes_app.core.presentation

import androidx.compose.runtime.Composable

data class CustomColorScheme(
    val primary: Long,
    val secondary: Long,
    val tertiary: Long,
)

// Define light and dark color schemes
val LightColorScheme = CustomColorScheme(
    primary = 0xFF6200EE,
    secondary = 0xFF03DAC6,
    tertiary = 0xFF018786
)

val DarkColorScheme = CustomColorScheme(
    primary = 0xFFBB86FC,
    secondary = 0xFF03DAC6,
    tertiary = 0xFFCF6679
)


@Composable
fun Theme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme
    PlatformTheme(isDarkTheme, colorScheme, content)
}