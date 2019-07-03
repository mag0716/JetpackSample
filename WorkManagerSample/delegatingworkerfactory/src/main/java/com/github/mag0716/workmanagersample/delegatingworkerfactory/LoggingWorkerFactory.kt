package com.github.mag0716.workmanagersample.delegatingworkerfactory

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class LoggingWorkerFactory : WorkerFactory() {

    override fun createWorker(appContext: Context,
                              workerClassName: String,
                              workerParameters: WorkerParameters): ListenableWorker? {
        Log.d(MainActivity.TAG, "createWorker")
        return null
    }
}