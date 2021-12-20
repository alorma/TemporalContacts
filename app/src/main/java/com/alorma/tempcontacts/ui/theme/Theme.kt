package com.alorma.tempcontacts.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  primary = Purple200,
  primaryVariant = Purple700,
  secondary = Teal200
)

private val LightColorPalette = lightColors(
  primary = Purple500,
  primaryVariant = Purple700,
  secondary = Teal200
)

@Composable
fun TempContactsTheme(content: @Composable() () -> Unit) {
  MaterialTheme(
    colors = LightColorPalette,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}