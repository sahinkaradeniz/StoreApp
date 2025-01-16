package com.skapps.fakestoreapp.coreui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.skapps.fakestoreapp.coreui.R


val poppinsFontFamily = FontFamily(
    fonts = listOf(
        androidx.compose.ui.text.font.Font(
            resId = R.font.poppins,
            weight = FontWeight.Normal,
            style = androidx.compose.ui.text.font.FontStyle.Normal
        ),
        androidx.compose.ui.text.font.Font(
            resId = R.font.poppins_medium,
            weight = FontWeight.Medium,
            style = androidx.compose.ui.text.font.FontStyle.Normal
        ),
        androidx.compose.ui.text.font.Font(
            resId = R.font.poppins_semibold,
            weight = FontWeight.SemiBold,
            style = androidx.compose.ui.text.font.FontStyle.Normal
        ),
        androidx.compose.ui.text.font.Font(
            resId = R.font.poppins_bold,
            weight = FontWeight.Bold,
            style = androidx.compose.ui.text.font.FontStyle.Normal
        )
    )
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)