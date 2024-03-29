package com.github.mag0716.composesamples

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Scanner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.mag0716.composesamples.ui.BoxWithConstraintsScopeScreen
import com.github.mag0716.composesamples.ui.RequiredSizeScreen
import com.github.mag0716.composesamples.ui.TextSampleScreen
import com.github.mag0716.composesamples.ui.GraphicsSampleScreen
import com.github.mag0716.composesamples.ui.AnimationSampleScreen
import com.github.mag0716.composesamples.ui.SampleList
import com.github.mag0716.composesamples.ui.UseMutableStateScreen
import com.github.mag0716.composesamples.ui.LaunchedEffectSampleScreen
import com.github.mag0716.composesamples.ui.RememberUpdatedStateSampleScreen
import com.github.mag0716.composesamples.ui.SideEffectSampleScreen
import com.github.mag0716.composesamples.ui.androiddevchallenge3.AndroidDevChallenge3Activity
import com.github.mag0716.composesamples.ui.theme.ComposeSamplesTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSamplesTheme {
                MainScreen()
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    private fun MainScreen(
        scaffoldState: ScaffoldState = rememberScaffoldState()
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            // TODO: 画面遷移したらUp keyを表示させたいが、TopAppBarはどこで実装すべき？
            topBar = {
                TopAppBar(
                    title = { Text(getString(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = {
                            Log.d(TAG, radiography.Radiography.scan())
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Scanner,
                                contentDescription = ""
                            )
                        }
                    }
                )
            }
        ) {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "sampleList") {
                composable("sampleList") {
                    SampleList(
                        Sample.values().toList()
                    ) { sample ->
                        when (sample) {
                            Sample.AndroidDevChallenge3 -> {
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        AndroidDevChallenge3Activity::class.java
                                    )
                                )
                            }
                            // Layout
                            Sample.RequiredSize -> {
                                navController.navigate("requiredSize")
                            }
                            Sample.BoxWithConstraints -> {
                                navController.navigate("boxWithConstraints")
                            }
                            Sample.Text -> {
                                navController.navigate("text")
                            }
                            Sample.Graphics -> {
                                navController.navigate("graphics")
                            }
                            Sample.Animation -> {
                                navController.navigate("animation")
                            }
                            // State
                            Sample.UseMutableState -> {
                                navController.navigate("useMutableState")
                            }
                            // Effect
                            Sample.UseCoroutineInComposable -> {
                                navController.navigate("useCoroutineInComposable")
                            }
                            Sample.RememberUpdatedState -> {
                                navController.navigate("rememberUpdatedState")
                            }
                            Sample.SideEffectSample -> {
                                navController.navigate("sideEffectSample")
                            }
                        }
                    }
                }
                composable("requiredSize") {
                    RequiredSizeScreen()
                }
                composable("boxWithConstraints") {
                    BoxWithConstraintsScopeScreen()
                }
                composable("text") {
                    TextSampleScreen()
                }
                composable("graphics") {
                    GraphicsSampleScreen()
                }
                composable("animation") {
                    AnimationSampleScreen()
                }
                composable("useMutableState") {
                    UseMutableStateScreen()
                }
                composable("useCoroutineInComposable") {
                    LaunchedEffectSampleScreen(scaffoldState)
                }
                composable("rememberUpdatedState") {
                    RememberUpdatedStateSampleScreen()
                }
                composable("sideEffectSample") {
                    SideEffectSampleScreen {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "ComposeSamples"
    }
}