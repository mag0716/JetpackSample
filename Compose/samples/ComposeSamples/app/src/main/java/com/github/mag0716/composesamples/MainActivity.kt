package com.github.mag0716.composesamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.github.mag0716.composesamples.ui.Dummy
import com.github.mag0716.composesamples.ui.SampleList
import com.github.mag0716.composesamples.ui.theme.ComposeSamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSamplesTheme {
                Scaffold(
                    // TODO: 画面遷移したらUp keyを表示させたいが、TopAppBarはどこで実装すべき？
                    topBar = {
                        TopAppBar(
                            title = { Text(getString(R.string.app_name)) }
                        )
                    }
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "sampleList") {
                        composable("sampleList") {
                            SampleList(
                                Sample.values().toList()
                            ) {
                                navController.navigate("dummy") {
                                }
                            }
                        }
                        composable("dummy") {
                            Dummy()
                        }
                    }
                }
            }
        }
    }
}