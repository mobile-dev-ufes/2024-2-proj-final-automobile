package com.ufes.automobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = White,
    primaryContainer = LightBlue,
    onPrimaryContainer = Black,

    secondary = Danube,
    onSecondary = White,
    secondaryContainer = Color(0xFFBFD6F5), // Azul mais claro para fundo de componentes
    onSecondaryContainer = Black,

    background = Color(0xFFF5F9FF), // Azul bem claro, quase branco
    onBackground = Black,

    surface = White,
    onSurface = Black,

    tertiary = Yellow,
    onTertiary = Black,
    tertiaryContainer = Color(0xFFFFE5A3), // Amarelo mais suave para evitar cansaço visual
    onTertiaryContainer = Black,

    error = Color(0xFFD32F2F),
    onError = White
)

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimary,
    onPrimary = White,
    primaryContainer = Color(0xFF082C70), // Azul mais escuro para não cansar a vista
    onPrimaryContainer = White,

    secondary = Danube,
    onSecondary = White,
    secondaryContainer = Color(0xFF445E89), // Azul acinzentado para fundo de elementos
    onSecondaryContainer = White,

    background = Color(0xFF121212), // Fundo real de dark mode
    onBackground = White,

    surface = Color(0xFF1E1E1E), // Cinza escuro para diferenciar de background
    onSurface = White,

    tertiary = Yellow,
    onTertiary = Black,
    tertiaryContainer = Color(0xFF826C2A), // Amarelo queimado para evitar brilho excessivo
    onTertiaryContainer = White,

    error = Color(0xFFCF6679),
    onError = Black
)


@Composable
fun AutoMobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    )
}