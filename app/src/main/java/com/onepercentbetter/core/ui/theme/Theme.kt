package com.onepercentbetter.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@SuppressLint("NewApi")
@Composable
fun OPBTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content:
        @Composable()
        () -> Unit,
) {
    val colors =
        if (isDark) {
            DarkThemeColors
        } else {
            LightThemeColors
        }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
//        shapes = Shapes,
        content = content,
    )
}
