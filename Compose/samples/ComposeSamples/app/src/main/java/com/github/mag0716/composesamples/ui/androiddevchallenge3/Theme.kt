package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.annotation.DrawableRes
import com.github.mag0716.composesamples.R

enum class Theme(
    val title: String,
                 @DrawableRes val resourceId: Int
) {
    DesertChick("Desert chic", R.drawable.desert_chic),
    TinyTerrariums("Tiny terrariums", R.drawable.tiny_terrariums),
    JungleVibes("Jungle vibes", R.drawable.jungle_vibes),
    EasyCare("Easy care", R.drawable.easy_care),
    Statements("Statements", R.drawable.statements)
}