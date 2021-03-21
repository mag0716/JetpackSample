package com.github.mag0716.composesamples.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComposeSamplesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

// Android Dev Challenge #3
@Composable
fun AndroidDevChallenge3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Green900,
            primaryVariant = Green900,
            onPrimary = Color.White,
            secondary = Green300,
            secondaryVariant = Green300,
            onSecondary = Gray,
            background = Gray,
            onBackground = Color.White,
            surface = White150,
            onSurface = White850
        )
    } else {
        lightColors(
            primary = Pink100,
            primaryVariant = Pink100,
            onPrimary = Gray,
            secondary = Pink900,
            secondaryVariant = Pink900,
            onSecondary = Color.White,
            background = Color.White,
            onBackground = Gray,
            surface = White850,
            onSurface = Gray
        )
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}