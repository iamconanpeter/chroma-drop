package com.conan.chroma.drop.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = androidx.compose.ui.graphics.Color(0xFFff9800),
    primaryVariant = androidx.compose.ui.graphics.Color(0xFFe65100),
    secondary = androidx.compose.ui.graphics.Color(0xFF03a9f4)
)

@Composable
fun ChromaDropTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = androidx.compose.material.Typography(),
        shapes = androidx.compose.material.Shapes(),
        content = content
    )
}
