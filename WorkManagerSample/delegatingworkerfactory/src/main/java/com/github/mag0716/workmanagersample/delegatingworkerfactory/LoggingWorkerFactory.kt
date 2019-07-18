package com.github.mag0716.workmanagersample.delegatingworkerfactory

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.mag0716.common.LoggingWorker

class LoggingWorkerFactory(val prefix: String) : WorkerFactory() {

    override fun createWorker(appContext: Context,
                              workerClassName: String,
                              workerParameters: WorkerParameters): ListenableWorker? {
        Log.d(MainActivity.TAG, "[$prefix]createWorker : worker=$workerClassName, parameters=$workerParameters")

        // WorkerFactory1 のみ呼び出される
        return LoggingWorker(appContext, workerParameters)

        // null を返すとデフォルトの WorkerFactory が利用される
        // WorkerFactory1, WorkerFactory2 とも呼び出される
        // return null
    }
}