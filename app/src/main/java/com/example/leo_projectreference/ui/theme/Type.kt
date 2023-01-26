package com.example.leo_projectreference.ui.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.leo_projectreference.R

private val UrbanistExtraBold = FontFamily(Font(R.font.urbanist_extrabold))
private val UrbanistBold = FontFamily(Font(R.font.urbanist_bold))
private val UrbanistLight = FontFamily(Font(R.font.urbanist_light))
private val UrbanistMedium = FontFamily(Font(R.font.urbanist_medium))
private val UrbanistSemiBold = FontFamily(Font(R.font.urbanist_semi_bold))

val typography = Typography(
    h1 = TextStyle(
        fontFamily = UrbanistExtraBold,
        fontSize = 40.sp,
    ),
    h2 = TextStyle(
        fontFamily = UrbanistExtraBold,
        fontSize = 36.sp
    ),
    h3 = TextStyle(
        fontFamily = UrbanistSemiBold,
        fontSize = 13.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = UrbanistMedium,
        fontSize = 15.sp
    ),
    body1 = TextStyle(
        fontFamily = UrbanistLight,
        fontSize = 13.sp
    ),
    button = TextStyle(
        fontFamily = UrbanistBold,
        fontSize = 13.sp
    )
)