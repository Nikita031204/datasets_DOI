package org.cross.cite.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF0B5D8C),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCDE5FF),
    onPrimaryContainer = Color(0xFF001D31),
    secondary = Color(0xFF243B54),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD3E4FA),
    onSecondaryContainer = Color(0xFF0C1D2E),
    tertiary = Color(0xFF00838F),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFF7F9FC),
    onBackground = Color(0xFF181C20),
    surface = Color(0xFFF7F9FC),
    onSurface = Color(0xFF181C20),
    surfaceVariant = Color(0xFFDEE3EB),
    onSurfaceVariant = Color(0xFF42474E),
    surfaceContainerLow = Color(0xFFF1F4F8),
    surfaceContainer = Color(0xFFFFFFFF),
    surfaceContainerHigh = Color(0xFFE8ECF1),
    outline = Color(0xFF72777F),
    outlineVariant = Color(0xFFC2C7CF),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF94CCFF),
    onPrimary = Color(0xFF003351),
    primaryContainer = Color(0xFF004B73),
    onPrimaryContainer = Color(0xFFCDE5FF),
    secondary = Color(0xFFB7C8DE),
    onSecondary = Color(0xFF213243),
    secondaryContainer = Color(0xFF38495B),
    onSecondaryContainer = Color(0xFFD3E4FA),
    tertiary = Color(0xFF7FD6E0),
    onTertiary = Color(0xFF00363C),
    background = Color(0xFF101418),
    onBackground = Color(0xFFE0E2E8),
    surface = Color(0xFF101418),
    onSurface = Color(0xFFE0E2E8),
    surfaceVariant = Color(0xFF42474E),
    onSurfaceVariant = Color(0xFFC2C7CF),
    surfaceContainerLow = Color(0xFF181C20),
    surfaceContainer = Color(0xFF1C2024),
    surfaceContainerHigh = Color(0xFF262A2F),
    outline = Color(0xFF8C9199),
    outlineVariant = Color(0xFF42474E),
)

@Composable
fun AppTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content,
    )
}
