package com.example.praktam_2457051019.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = OrangeSecondary,
    background = BackgroundLight,
    surface = SurfaceWhite,
    onPrimary = Color.White,
    onSurface = TextDark,
    tertiary = BlueDark,
    surfaceVariant = ImageBackground,
    onSurfaceVariant = GrayText
)

@Composable
fun PraktamTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content
    )
}