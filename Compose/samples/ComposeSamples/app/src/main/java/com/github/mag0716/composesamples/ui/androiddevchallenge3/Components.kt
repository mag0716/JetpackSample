package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MediumShapeContainedButtonWithSecondaryColor(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
        )
    }
}

@Composable
fun MediumShapeTextButtonWithSecondaryColor(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colors.secondary
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Text(
            text,
            style = MaterialTheme.typography.button,
        )
    }
}

@Preview
@Composable
fun MediumShapeContainedButtonWithSecondaryColorPreview() {
    MediumShapeContainedButtonWithSecondaryColor(
        text = "Button",
        onClick = {}
    )
}

@Preview
@Composable
fun MediumShapeTextButtonWithSecondaryColorPreview() {
    MediumShapeTextButtonWithSecondaryColor(
        text = "Button",
        onClick = {}
    )
}