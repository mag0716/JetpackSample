package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mag0716.composesamples.R

@Composable
fun WelcomeScreen(
    createAccount: () -> Unit,
    logIn: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.primary
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(
                state = rememberScrollState(0)
            )
        ) {
            Spacer(modifier = Modifier.height(72.dp))
            Image(
                painter = painterResource(id = R.drawable.welcome_illos),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 88.dp)
            )
            Spacer(modifier = Modifier.height(48.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
            )
            Text(
                text = "Beautiful home garden solutions",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.paddingFromBaseline(
                    top = 32.dp,
                    bottom = 40.dp
                )
            )
            Button(
                onClick = createAccount,
                colors = buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSecondary
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.button,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                onClick = logIn,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.secondary
                ),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
            ) {
                Text("Log in")
            }
        }
    }
}


@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        createAccount = {},
        logIn = {}
    )
}