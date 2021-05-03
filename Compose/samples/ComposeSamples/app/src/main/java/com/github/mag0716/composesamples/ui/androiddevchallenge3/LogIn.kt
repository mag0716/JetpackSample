package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mag0716.composesamples.ui.theme.AndroidDevChallenge3Theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun LogInScreen(
    login: () -> Unit
) {
    var eMailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LogInScreen(
        eMailAddress = eMailAddress,
        onChangeEMailAddress = { eMailAddress = it },
        password = password,
        onChangePassword = { password = it },
        // TODO:ログイン処理をViewModel側に委譲
        login = login
    )
}

@Composable
fun LogInScreen(
    eMailAddress: String,
    onChangeEMailAddress: (String) -> Unit,
    password: String,
    onChangePassword: (String) -> Unit,
    login: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(
                state = rememberScrollState(0)
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Log in with email",
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .paddingFromBaseline(
                    top = 184.dp,
                    bottom = 16.dp
                )
        )
        OutlinedTextField(
            value = eMailAddress,
            onValueChange = onChangeEMailAddress,
            label = {
                Text(
                    "Email address",
                    style = MaterialTheme.typography.body1
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onChangePassword,
            label = {
                Text(
                    "Password (8+ characters)",
                    style = MaterialTheme.typography.body1
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Text(
            buildAnnotatedString {
                append("By clicking below, you agree to our ")
                withStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline)
                ) {
                    append("Team of Use")
                }
                append(" and consent\n")
                append("to our ")
                withStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline)
                ) {
                    append("Privacy Policy")
                }
                append(".")
            },
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.paddingFromBaseline(
                top = 24.dp,
                bottom = 16.dp
            )
        )
        MediumShapeContainedButtonWithSecondaryColor(
            text = "Log in",
            onClick = login,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun LogInScreenPreview() {
    AndroidDevChallenge3Theme {
        LogInScreen {
        }
    }
}