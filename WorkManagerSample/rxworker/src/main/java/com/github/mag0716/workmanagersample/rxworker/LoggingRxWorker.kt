package com.github.mag0716.workmanagersample.rxworker

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class LoggingRxWorker(context: Context, workerParameters: WorkerParameters) : RxWorker(context, workerParameters) {
    companion object {
        private const val KEY = "Tag"
        fun createInputData(tag: String) = workDataOf(KEY to tag)
    }

    override fun createWork(): Single<Result> {
        val tag = inputData.getString(KEY)
        Log.d(tag, "doWork start...")
        return Single.just(tag)
                .delay(3, TimeUnit.SECONDS)
                .doOnSubscribe { Log.d(tag, "createWork start...") }
                .doOnDispose { Log.d(tag, "createWork finish!!") }
                .map { Result.success() }
    }
}