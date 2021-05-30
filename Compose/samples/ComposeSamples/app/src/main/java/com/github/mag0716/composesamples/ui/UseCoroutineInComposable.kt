package com.github.mag0716.composesamples.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

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
    // LaunchedEffectもrememberCoroutineScopeも@Composableでsuspend funを実行するためのもの
    // @Composableの直下はLaunchedEffect, @Composable直下でない場合はrememberCoroutineScope()
    // @ComposableがCompositionから削除された場合はCoroutineはキャンセルされるのは同じ動作
    val coroutineScope = rememberCoroutineScope()
    if (isError) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = "Error message"
            )
        }
    }

    Button(
        onClick = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Message"
                )
            }
        }
    ) {
        Text("Show SnackBar")
    }
}