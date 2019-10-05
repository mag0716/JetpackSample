package com.github.mag0716.savedstatesample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "SavedStateSample"
        private const val KEY_SAVED_STATE = "SavedState"
        private const val KEY_TIME = "time"
    }

    private lateinit var saveButton: Button
    private lateinit var restoreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton = findViewById(R.id.save_button)
        restoreButton = findViewById(R.id.restore_button)
        restoreButton.setOnClickListener { restore() }

        savedStateRegistry.registerSavedStateProvider(KEY_SAVED_STATE) {
            val current = System.currentTimeMillis()
            Log.d(TAG, "save : $current")
            Bundle().apply {
                putLong(KEY_TIME, current)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }

    private fun restore() {
        val savedState = savedStateRegistry.consumeRestoredStateForKey(KEY_SAVED_STATE)
        // Activity が破棄されていないと取得できない
        savedState?.let {
            Log.d(TAG, "restore : ${it.getLong(KEY_TIME)}")
        } ?: Log.d(TAG, "restore")
    }
}
