package com.github.mag0716.composesamples.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.mag0716.composesamples.Sample

@Composable
fun SampleList(samples: List<Sample>, navigateToSample: (Sample) -> Unit) {
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        samples.forEach { sample ->
            SampleItem(sample, navigateToSample)
            Divider()
        }
    }
}

@Composable
fun SampleItem(sample: Sample, navigateToSample: (Sample) -> Unit) {
    Text(
        text = sample.name,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToSample(sample)
            }
            .padding(16.dp)
    )
}

@Preview
@Composable
fun SampleItemPreview() {
    SampleItem(Sample.SAMPLE1) {
    }
}