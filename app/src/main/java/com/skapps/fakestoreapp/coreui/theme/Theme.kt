package com.skapps.fakestoreapp.coreui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = com.skapps.fakestoreapp.coreui.theme.Purple80,
    secondary = com.skapps.fakestoreapp.coreui.theme.PurpleGrey80,
    tertiary = com.skapps.fakestoreapp.coreui.theme.Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = com.skapps.fakestoreapp.coreui.theme.Purple40,
    secondary = com.skapps.fakestoreapp.coreui.theme.PurpleGrey40,
    tertiary = com.skapps.fakestoreapp.coreui.theme.Pink40
)

@Composable
fun FakeStoreAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = com.skapps.fakestoreapp.coreui.theme.Typography,
        content = content
    )
}