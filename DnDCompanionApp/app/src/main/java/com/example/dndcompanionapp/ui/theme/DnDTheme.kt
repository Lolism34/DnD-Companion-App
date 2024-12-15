package com.example.dndcompanionapp.ui.theme
//
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color

// Colors for the light theme (Red and White)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF0000),      // Bright red for primary elements
    secondary = Color(0xFFFFCDD2),   // Light red for secondary elements
    background = Color(0xFFFFFFFF),  // White background
    surface = Color(0xFFFFFFFF),     // White surface
    onPrimary = Color.White,         // White text/icons on red
    onSecondary = Color.Black,       // Black text/icons on light red
    onBackground = Color.Black,      // Black text on white
    onSurface = Color.Black          // Black text on white surfaces
)

// Colors for the dark theme (Black and White)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),     // White for primary elements
    secondary = Color(0xFF424242),   // Dark gray for secondary elements
    background = Color(0xFF000000),  // Black background
    surface = Color(0xFF121212),     // Dark gray surface
    onPrimary = Color.Black,         // Black text/icons on white
    onSecondary = Color.White,       // White text/icons on dark gray
    onBackground = Color.White,      // White text on black
    onSurface = Color.White          // White text on dark gray surfaces
)

@Composable
fun DnDTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) LightColorScheme else DarkColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
