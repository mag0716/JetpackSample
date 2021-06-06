package com.github.mag0716.composesamples.ui

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*

private const val TAG = "SideEffectSample"

@Composable
fun SideEffectSampleScreen(
    onBack: () -> Unit
) {
    var isBackHandlerEnabled: Boolean by remember { mutableStateOf(true) }
    BackHandlerDebug(
        enabled = isBackHandlerEnabled,
        onBack = onBack
    )
    Column {
        Row {
            Text("BackHandler ON/OFF")
            Checkbox(
                checked = isBackHandlerEnabled,
                onCheckedChange = {
                    isBackHandlerEnabled = isBackHandlerEnabled.not()
                }
            )
        }
        Text("SideEffectSample")
    }
}

@Composable
fun BackHandlerDebug(
    enabled: Boolean = true,
    onBack: () -> Unit
) {
    val currentOnBack by rememberUpdatedState(onBack)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                Log.d(TAG, "handleOnBackPressed")
                currentOnBack()
            }
        }
    }
    // Compositionの失敗でOnBackPressedCallbackのenabledが不整合な状態にならないようにSideEffectを利用しているがSideEffectを利用していなくても正常に動作する
    // Compositionが失敗するというのはどういうケースなのか？
    SideEffect {
        Log.d(TAG, "SideEffect : $enabled")
        backCallback.isEnabled = enabled
    }
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(backDispatcher) {
        backDispatcher?.addCallback(backCallback)
        onDispose {
            Log.d(TAG, "dispose : ${backCallback.isEnabled}")
            backCallback.remove()
        }
    }
}