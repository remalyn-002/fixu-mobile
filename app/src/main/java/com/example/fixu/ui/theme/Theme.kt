package com.example.fixu.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = HighContrastCyan,
    background = DarkCharcoal,
    surface = DarkSurface,
    onPrimary = DarkCharcoal,
    onBackground = TextWhite,
    onSurface = TextWhite
)

private val LightColorScheme = lightColorScheme(
    primary = HighContrastCyan,
    background = TextWhite,
    surface = DarkSurface,
    onPrimary = DarkCharcoal,
    onBackground = DarkCharcoal,
    onSurface = TextWhite
)

@Composable
fun FixUTheme(
    selectedTheme: String = "Dark",
    content: @Composable () -> Unit
) {
    val useDarkTheme = when (selectedTheme) {
        "Light" -> false
        "Dark" -> true
        else -> isSystemInDarkTheme()
    }

    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}