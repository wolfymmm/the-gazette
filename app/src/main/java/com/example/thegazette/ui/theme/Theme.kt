package com.example.thegazette.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Зелені відтінки для екранів
val OliveGreenLight = Color(0xFF3E5A44) // Для світлої теми
val OliveGreenDark = Color(0xFF7AA885)  // Для темної теми

private val LightColorScheme = lightColorScheme(
    primary = WineAccentLight,
    onPrimary = PaperLight,
    secondary = OliveBorderLight,
    onSecondary = PaperLight,
    tertiary = OliveGreenLight, // Зелений колір іконок/тексту для світлої теми
    background = PaperLight,
    onBackground = InkDark,
    surface = PaperSurfaceLight,
    onSurface = InkDark,
    outline = OliveBorderLight
)

private val DarkColorScheme = darkColorScheme(
    primary = WineAccentDark,
    onPrimary = PaperDark,
    secondary = OliveBorderDark,
    onSecondary = InkLight,
    tertiary = OliveGreenDark, // Зелений колір іконок/тексту для темної теми
    background = PaperDark,
    onBackground = InkLight,
    surface = PaperSurfaceDark,
    onSurface = InkLight,
    outline = OliveBorderDark
)

@Composable
fun TheGazetteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}