package com.example.androidpractice.core.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

val LightThemeColors
    @Composable get() = lightColors(
        primary = leaf,
        onPrimary = whiteColor
    )

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(colors = LightThemeColors, content = content, typography = Type)
}
