package com.github.mag0716.composesamples.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GraphicsSampleScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = rememberScrollState(0)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("drawPoints")
        PointsSample()
        Text("drawLine")
        LineSample()
        Text("drawRect")
        RectSample()
        Text("drawRoundRect")
        RoundRectSample()
        Text("drawCircle")
        CircleSample()
        Text("drawArc")
        ArcSample()
        Text("drawOval")
        OvalSample()
        Text("drawPath")
        PathSample()
    }
}

@Preview
@Composable
fun PointsSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        drawPoints(
            points = listOf(
                Offset(10f, 10f),
                Offset(size.width - 10f, 10f),
                Offset(size.width - 10f, size.height - 10f),
                Offset(10f, size.height - 10f)
            ),
            pointMode = PointMode.Points,
            color = Color.Red,
            strokeWidth = 10f
        )
    }
}

@Preview
@Composable
fun LineSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        drawLine(
            color = Color.Red,
            start = Offset(x = 10f, y = size.height / 2f),
            end = Offset(x = size.width - 10f, y = size.height / 2f),
        )
    }
}

@Preview
@Composable
fun RectSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        drawRect(
            color = Color.Red,
            topLeft = Offset(x = 10f, y = 10f),
            size = Size(width = size.width - 20f, height = size.height - 20f)
        )
    }
}

@Preview
@Composable
fun RoundRectSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        drawRoundRect(
            color = Color.Red,
            topLeft = Offset(x = 10f, y = 10f),
            size = Size(width = size.width - 20f, height = size.height - 20f),
            cornerRadius = CornerRadius(x = 50f, y = 50f)
        )
    }
}

@Preview
@Composable
fun CircleSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        drawCircle(
            color = Color.Red
        )
    }
}

@Preview
@Composable
fun ArcSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        drawArc(
            startAngle = -150f,
            sweepAngle = 120f,
            useCenter = true,
            color = Color.Red
        )
    }
}

@Preview
@Composable
fun OvalSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        // デフォルトパラメータのままだとdrawCircleと同じ
        // sizeのwidth, heightが同じだとcircleとして描画される
        drawOval(
            topLeft = Offset(10f, size.height / 4f),
            size = Size(size.width - 20f, size.height / 2f),
            color = Color.Red
        )
    }
}

@Preview
@Composable
fun PathSample() {
    Canvas(
        Modifier
            .size(100.dp)
            .border(
                width = 1.dp,
                color = Color.Black
            )
    ) {
        drawPath(
            path = Path().apply {
                val padding = 10f
                moveTo(padding, size.height - padding)
                relativeLineTo((size.width - padding * 2) / 2f, -(size.height - padding * 2))
                relativeLineTo((size.width - padding * 2) / 2f, size.height - padding * 2)
                relativeLineTo(-(size.width - padding), -(size.height / 2f + 20f))
                relativeLineTo(size.width, 0f)
                close()
            },
            color = Color.Red
        )
    }
}