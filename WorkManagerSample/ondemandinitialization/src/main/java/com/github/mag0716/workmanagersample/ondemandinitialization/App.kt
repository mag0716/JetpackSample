package com.github.mag0716.workmanagersample.ondemandinitialization

import android.app.Application
import androidx.work.Configuration

class App : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build()
    }
}