package com.codelab.theming.ui.start.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val JetnewsShapes = Shapes(
    small = CutCornerShape(topLeft = 8.dp),
    medium = CutCornerShape(topLeft = 24.dp),
    large = RoundedCornerShape(8.dp)
)