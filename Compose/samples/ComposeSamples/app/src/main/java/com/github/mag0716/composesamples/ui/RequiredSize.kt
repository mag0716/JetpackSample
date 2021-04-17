package com.github.mag0716.composesamples.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RequiredSizeScreen() {
    Column {
        Text("non requiredSize")
        NonRequiredSizeSample()
        Text("use requiredSize")
        RequiredSizeSample()
    }
}

@Preview
@Composable
fun RequiredSizeSample() {
    Box(
        Modifier
            .size(90.dp, 150.dp)
            .background(Color.Green)
    ) {
        Box(
            Modifier
                .requiredSize(100.dp, 100.dp)
                .background(Color.Red)
        )
    }
}

@Preview
@Composable
fun NonRequiredSizeSample() {
    Box(
        Modifier
            .size(90.dp, 150.dp)
            .background(Color.Green)
    ) {
        Box(
            Modifier
                .size(100.dp, 100.dp)
                .background(Color.Red)
        )
    }
}