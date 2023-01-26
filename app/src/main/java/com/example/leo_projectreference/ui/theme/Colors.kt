package com.example.leo_projectreference.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

private val PrimaryBlue = Color(0xFF0A3751)
private val SecondaryOrange = Color(0xFFFC8138)
private val BackgroundBlue = Color(0xFFF4F4fE)
private val OnBackgroundBlack = Color(0xFFF4F4fE)

val lightColorPallete = lightColors(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    secondary = SecondaryOrange,
    onSecondary = Color.White,
    background = BackgroundBlue,
    onBackground = OnBackgroundBlack,
    surface = Color.White,
    onSurface = PrimaryBlue
)

val darkColorPallete = darkColors()
