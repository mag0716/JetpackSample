package com.github.mag0716.common

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class LoggingWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    companion object {
        private const val KEY_TAG = "Tag"
        private const val KEY_COUNT = "Count"
        fun createInputData(tag: String) = workDataOf(KEY_TAG to tag)
        fun createInputData(tag: String, count: Int) = workDataOf(KEY_TAG to tag, KEY_COUNT to count)
    }

    override fun doWork(): Result {
        val tag = inputData.getString(KEY_TAG)
        val count = inputData.getInt(KEY_COUNT, 0)
        Log.d(tag, "doWork start... : $count")
        Thread.sleep(3000)

        if (isStopped) {
            return Result.failure()
        }

        Log.d(tag, "doWork finish!! : $runAttemptCount")
        if (count == 0) {
            return Result.success()
        } else {
            if (runAttemptCount < count) {
                return Result.retry()
            } else {
                return Result.success()
            }
        }
    }
}