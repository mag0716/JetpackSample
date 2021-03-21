package com.github.mag0716.composesamples.ui.androiddevchallenge3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.github.mag0716.composesamples.ui.theme.AndroidDevChallenge3Theme

class AndroidDevChallenge3Activity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidDevChallenge3Theme {
                Text("Android Dev Challenge #3")
            }
        }
    }
}