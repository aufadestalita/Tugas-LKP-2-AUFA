package com.example.aufapunya.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Schema warna mengikuti identitas visual (Langkah 5 Modul)
private val AppColorScheme = lightColorScheme(
    primary = Purple80,    // [cite: 188]
    secondary = PurpleGrey80, // [cite: 189]
    background = Pink80, // [cite: 190]
    surface = Purple40,      // [cite: 191]
    onPrimary = PurpleGrey40   // [cite: 192]
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

@Composable
fun AufaPunyaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set dynamicColor ke false agar warna custom Orange-Cream kita yang muncul (sesuai modul)
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> AppColorScheme // Menggunakan skema warna yang kita susun [cite: 196]
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Menghubungkan ke AppTypography di Type.kt [cite: 197]
        content = content
    )
}