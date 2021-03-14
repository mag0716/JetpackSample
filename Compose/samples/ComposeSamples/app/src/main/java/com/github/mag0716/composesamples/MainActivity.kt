package com.github.mag0716.composesamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mag0716.composesamples.ui.theme.ComposeSamplesTheme
import com.github.mag0716.composesamples.ui.theme.Typography

// TODO: 実サンプルへの修正
enum class Sample {
    SAMPLE1,
    SAMPLE2,
    SAMPLE3,
    SAMPLE4,
    SAMPLE5,
    SAMPLE6,
    SAMPLE7,
    SAMPLE8,
    SAMPLE9,
    SAMPLE10,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SampleList(
                        Sample.values().toList()
                    )
                }
            }
        }
    }
}

@Composable
fun SampleList(samples: List<Sample>) {
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        samples.forEach { sample ->
            SampleItem(sample)
            Divider()
        }
    }
}

@Composable
fun SampleItem(sample: Sample) {
    Text(
        text = sample.name,
        style = Typography.h4,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview
@Composable
fun SampleItemPreview() {
    SampleItem(Sample.SAMPLE1)
}