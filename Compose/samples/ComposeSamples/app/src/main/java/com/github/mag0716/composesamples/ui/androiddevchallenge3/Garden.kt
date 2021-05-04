package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.annotation.DrawableRes
import com.github.mag0716.composesamples.R

enum class Garden(
    val title: String,
    val description: String,
    @DrawableRes val resourceId: Int
) {
    Monstera("Monstera", "This is a description", R.drawable.monstera),
    Aglaonema("Aglaonema", "This is a description", R.drawable.aglaonema),
    PeachLily("Peace lily", "This is a description", R.drawable.peach_lily),
    FiddleLeafTree("Fiddle leaf tree", "This is a description", R.drawable.fiddle_leaf_tree),
    SnakePlant("Snake plant", "This is a description", R.drawable.snake_plant),
    Pothos("Pothos", "This is a description", R.drawable.pothos)
}