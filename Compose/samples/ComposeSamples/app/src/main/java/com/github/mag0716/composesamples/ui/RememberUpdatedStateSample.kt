package com.github.mag0716.composesamples.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun RememberUpdatedStateSampleScreen() {
    var isLanding: Boolean by remember { mutableStateOf(true) }
    Column {
        Row {
            Text("起動中 ON/OFF")
            Checkbox(
                checked = isLanding,
                onCheckedChange = {
                    isLanding = isLanding.not()
                }
            )
        }
        LandingScreen(isLanding = isLanding) {
            isLanding = false
        }
    }
}

@Composable
private fun LandingScreen(
    isLanding: Boolean,
    onTimeout: () -> Unit
) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    LaunchedEffect(true) {
        delay(5000)
        currentOnTimeout()
    }

    if (isLanding) {
        Text("LandingScreen")
    } else {
        Text("Content")
    }
}