package com.github.mag0716.savedstatesample.withviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.mag0716.savedstatesample.withviewmodel.MainActivity.Companion.TAG

class MainViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_TIME = "time"
    }

    private val _createdTime: MutableLiveData<Long> = savedStateHandle.getLiveData<Long>(KEY_TIME)
    val createdTime: LiveData<Long> = _createdTime

    fun update() {
        val currentTime = System.currentTimeMillis()
        _createdTime.value = currentTime
        savedStateHandle.set(KEY_TIME, currentTime)
        Log.d(TAG, "MainViewModel : ${createdTime.value}")
    }
}