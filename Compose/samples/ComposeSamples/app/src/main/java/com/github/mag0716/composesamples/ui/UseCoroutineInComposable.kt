package com.github.mag0716.composesamples.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun LaunchedEffectSampleScreen(
    scaffoldState: ScaffoldState
) {
    var isError: Boolean by remember { mutableStateOf(false) }
    Column {
        Row {
            Text("エラー表示 ON/OFF")
            Checkbox(
                checked = isError,
                onCheckedChange = {
                    isError = isError.not()
                }
            )
        }
        LaunchedEffectSample(
            isError = isError,
            scaffoldState = scaffoldState
        )
    }
}

@Composable
private fun LaunchedEffectSample(
    isError: Boolean,
    scaffoldState: ScaffoldState
) {
    if (isError) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Error message"
            )
        }
    }
}