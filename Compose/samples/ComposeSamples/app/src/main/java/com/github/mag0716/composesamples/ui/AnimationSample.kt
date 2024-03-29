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
        Text("animateContentSize")
        AnimateContentSizeSample()
        Text("Crossfade")
        CrossfadeSample()
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
                .size(100.dp)
                .background(color = Color.Red)

        )
    }
}

@Composable
fun AnimateContentSizeSample() {
    var contentSize by remember { mutableStateOf(100.dp) }
    Row {
        Button(onClick = { contentSize += 100.dp }) {
            Text("size up")
        }
        Button(onClick = { contentSize -= 100.dp }) {
            Text("size down")
        }
    }
    Box(
        modifier = Modifier
            .animateContentSize()
            .size(contentSize)
            .background(color = Color.Red)
    )
}

@Composable
fun CrossfadeSample() {
    var isVisible by remember { mutableStateOf(true) }
    Button(onClick = {
        isVisible = isVisible.not()
    }) {
        Text("toggle visibility")
    }
    Crossfade(
        targetState = isVisible
    ) {
        if (it) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color.Red)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color.Green)
            )
        }
    }
}