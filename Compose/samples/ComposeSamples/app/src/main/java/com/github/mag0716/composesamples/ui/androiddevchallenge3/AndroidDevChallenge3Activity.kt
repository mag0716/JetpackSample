package com.github.mag0716.composesamples.ui.androiddevchallenge3

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.github.mag0716.composesamples.ui.theme.AndroidDevChallenge3Theme

class AndroidDevChallenge3Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // FIXME: use WindowInsets
        findViewById<View>(android.R.id.content).systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        setContent {
            AndroidDevChallenge3Theme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(
                            createAccount = {},
                            logIn = {
                                navController.navigate("logIn")
                            }
                        )
                    }
                    composable("logIn") {
                        LogInScreen(
                            login = {
                                navController.navigate("home")
                            }
                        )
                    }
                    composable("home") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}