package com.github.mag0716.composesamples.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun AnimationSampleScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = rememberScrollState(0)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("AnimatedVisibility")
        VisibilityAnimationSample()
    }
}

@ExperimentalAnimationApi
@Composable
fun VisibilityAnimationSample() {
    var isVisible by remember { mutableStateOf(true) }
    Button(onClick = {
        isVisible = isVisible.not()
    }) {
        Text("toggle visibility")
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(color = Color.Red)

        )
    }
}