package com.xingkong.monitor.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 星控紫金黑配色（与图标集同源）
private val XingkongDark = darkColorScheme(
    primary = Color(0xFF7C20C2),
    secondary = Color(0xFFD4B06F),
    tertiary = Color(0xFFF1DDA1),
    background = Color(0xFF050013),
    surface = Color(0xFF12031F),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val XingkongLight = lightColorScheme(
    primary = Color(0xFF7C20C2),
    secondary = Color(0xFFD4B06F),
    tertiary = Color(0xFFF1DDA1),
    background = Color(0xFFFFFBFF),
    surface = Color(0xFFFFFBFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun XingkongTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) XingkongDark else XingkongLight
    MaterialTheme(colorScheme = colors, content = content)
}
