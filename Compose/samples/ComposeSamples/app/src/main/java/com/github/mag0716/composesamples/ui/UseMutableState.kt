package com.github.mag0716.composesamples.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UseMutableStateScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ExpandingCard(
            title = "title",
            body = """
                body
                body
                body
                body
                body
                body
                body
                body
                body
                body
            """.trimIndent()
        )
    }
}

// https://developer.android.com/jetpack/compose/state#use-rememeber-to-create-internal-state-in-composable
@Composable
fun ExpandingCard(title: String, body: String, initialExpanded: Boolean = false) {
    var expandedState = remember { mutableStateOf(initialExpanded) }
    Card {
        Column(
            Modifier
                .width(280.dp)
                .animateContentSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(text = title)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (expandedState.value) {
                    Text(text = body)

                    Icon(
                        imageVector = Icons.Default.ExpandLess,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            expandedState.value = false
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            expandedState.value = true
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpandingCardPreview() {
    ExpandingCard(
        title = "title",
        body = """
                body
                body
                body
                body
                body
                body
                body
                body
                body
                body
            """.trimIndent(),
        initialExpanded = true
    )
}