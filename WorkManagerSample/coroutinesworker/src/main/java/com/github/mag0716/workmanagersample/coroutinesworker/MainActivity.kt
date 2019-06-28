package com.github.mag0716.workmanagersample.coroutinesworker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "CoroutinesWorker"
    }

    var work: OneTimeWorkRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener { startOneTimeWork() }
        cancelButton.setOnClickListener { cancel() }
    }

    private fun startOneTimeWork() {
        work = OneTimeWorkRequestBuilder<LoggingCoroutineWorker>()
                .setInputData(LoggingCoroutineWorker.createInputData(TAG))
                .build()
        val work = work
        if (work != null) {
            // ENQUEUED -> RUNNING -> SUCCEEDED の順だが、ENQUEUED が出力されない場合もある
            WorkManager.getInstance().getWorkInfoByIdLiveData(work.id)
                    .observe(this, Observer { status ->
                        Log.d(TAG, "observe : status = $status")
                    })
            WorkManager.getInstance().enqueue(work)
        }
    }

    private fun cancel() {
        val work = work
        if (work != null) {
            // CANCELLED が呼ばれる
            // LoggingCoroutineWorker#doWork 内の処理は自動的にキャンセルされる
            WorkManager.getInstance().cancelWorkById(work.id)
        }
    }
}
