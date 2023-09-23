package com.example.projectreference.core.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("NewApi")
@Composable
fun TOATheme(
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
