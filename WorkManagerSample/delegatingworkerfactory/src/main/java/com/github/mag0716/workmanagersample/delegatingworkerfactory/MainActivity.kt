package com.github.mag0716.workmanagersample.delegatingworkerfactory

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mag0716.common.LoggingWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "DelegatingWorkerFactory"
    }

    private val workerFactory = DelegatingWorkerFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val configuration = Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        workerFactory.addFactory(LoggingWorkerFactory("WorkerFactory1"))
        workerFactory.addFactory(LoggingWorkerFactory("WorkerFactory2"))
        WorkManager.initialize(this, configuration)

        button.setOnClickListener { startOneTimeWork() }
    }

    private fun startOneTimeWork() {
        val work = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData(TAG))
                .build()
        // ENQUEUED -> RUNNING -> SUCCEEDED の順だが、ENQUEUED が出力されない場合もある
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(work.id)
                .observe(this, Observer { status ->
                    Log.d(TAG, "observe : status = $status")
                })
        WorkManager.getInstance(this).enqueue(work)
    }
}
