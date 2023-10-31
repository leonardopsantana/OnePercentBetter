package com.onepercentbetter.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@SuppressLint("NewApi")
@Composable
fun OnePercentBetterTheme(
    content: @Composable() () -> Unit
) {
    val colors = lightColorPallete

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}
