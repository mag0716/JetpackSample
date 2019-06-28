package com.github.mag0716.workmanagersample.coroutinesworker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.delay

class LoggingCoroutineWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {
    companion object {
        private const val KEY = "Tag"
        fun createInputData(tag: String) = workDataOf(KEY to tag)
    }

    override suspend fun doWork(): Result {
        val tag = inputData.getString(KEY)
        return work(tag)
    }

    private suspend fun work(tag: String?): Result {
        Log.d(tag, "doWork start..")

        delay(3000)

        Log.d(tag, "doWork finish")
        return Result.success()
    }
}