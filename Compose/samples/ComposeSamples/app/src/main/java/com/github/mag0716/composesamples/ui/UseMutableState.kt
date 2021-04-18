package com.github.mag0716.composesamples.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun UseMutableStateScreen() {

}

// https://developer.android.com/jetpack/compose/state#use-rememeber-to-create-internal-state-in-composable
@Composable
fun ExpandingCard(title: String, body: String) {

}

@Preview
@Composable
fun ExpandingCardPreview() {
    ExpandingCard(
        title = "title",
        body = "body\nbody\nbody\nbody\nbody\nbody\nbody\nbody\nbody\nbody\n"
    )
}