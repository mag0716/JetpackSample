package com.github.mag0716.savedstatesample.withviewmodel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "SavedStateSample"
    }

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(
            this,
            SavedStateViewModelFactory(application, this)
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            mainViewModel.update()
        }

        Log.d(TAG, "onCreate : ${mainViewModel.createdTime.value}")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }
}
