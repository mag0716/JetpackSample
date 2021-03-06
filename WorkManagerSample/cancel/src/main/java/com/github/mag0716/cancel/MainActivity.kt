package com.github.mag0716.cancel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.github.mag0716.common.LoggingWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Cancel"
    }


    var work: OneTimeWorkRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enqueue_button.setOnClickListener { enqueue() }
        cancel_button.setOnClickListener { cancel() }
    }


    private fun enqueue() {
        work = OneTimeWorkRequestBuilder<LoggingWorker>()
                .setInputData(LoggingWorker.createInputData(TAG))
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
            // LoggingWorker#doWork 内の処理が止まるわけではない。必要があれば、Worker 側でキャンセルされたかどうかをチェックする
            // SUCCEEDED 後にキャンセルしても何も出力されない
            WorkManager.getInstance().cancelWorkById(work.id)
        }
    }
}
