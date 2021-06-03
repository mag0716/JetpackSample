package com.github.mag0716.composesamples.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val TAG = "TextSample"

@Composable
fun TextSampleScreen() {
    MaterialTheme {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            PartiallySelectableText()
            Spacer(modifier = Modifier.height(16.dp))
            SimpleClickableText()
            Spacer(modifier = Modifier.height(16.dp))
            AnnotatedClickableText()
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun PartiallySelectableText() {
    SelectionContainer {
        Column {
            Text("This text is selectable")
            Text("This one too")
            Text("This one as well")
            DisableSelection {
                Text("But not this one")
                Text("Neither this one")
            }
            Text("But again, you can select this one")
            Text("And this one too")
        }

    }
}

@Preview
@Composable
fun SimpleClickableText() {
    ClickableText(
        text = AnnotatedString("Click Me"),
        onClick = { offset ->
            Log.d(TAG, "$offset -th character is clicked.")
        }
    )
}

@Preview
@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        append("Click ")

        pushStringAnnotation(
            tag = "URL",
            annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("here")
        }

        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "URL", start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    Log.d(TAG, annotation.item)
                }
        }
    )
}

@Preview
@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )
}