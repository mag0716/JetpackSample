package com.github.mag0716.navigationsample.navgraphviewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SampleViewModel(
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_COUNT = "count"
    }

    var count: Int = savedStateHandle.get<Int>(KEY_COUNT) ?: 0
        private set

    fun increment() {
        count++
        savedStateHandle.set(KEY_COUNT, count)
    }
}