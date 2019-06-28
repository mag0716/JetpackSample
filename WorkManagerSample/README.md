# WorkManagerSample

## 対象バージョン

* 1.0.0-alpha08

## サンプル

| モジュール名 | 概要 | 作成バージョン | 備考 |
| - | - | - | - |
| onetime | Worker を1度実行するサンプル | 1.0.0-alpha08 | |
| periodic | Worker を定期的に実行するサンプル | 1.0.0-alpha08 | interval を1分に指定しているが、最小リピート間隔は呼び出す API や端末の状態による |
| cancel | ID を指定してキャンセルするサンプル | 1.0.0-alpha08 | |
| chainedtask | 2つの Worker を直列、並列で実行するサンプル | 1.0.0-alpha08 | |

### periodic

```
2018-08-30 22:17:08.026 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=ENQUEUED, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:17:08.036 24260-24301/com.github.mag0716.periodic D/PeriodicWork: doWork start...
2018-08-30 22:17:08.100 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=RUNNING, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:17:11.039 24260-24301/com.github.mag0716.periodic D/PeriodicWork: doWork finish!!
2018-08-30 22:17:11.055 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=ENQUEUED, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:32:13.951 24260-24415/com.github.mag0716.periodic D/PeriodicWork: doWork start...
2018-08-30 22:32:13.957 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=RUNNING, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
2018-08-30 22:32:16.953 24260-24415/com.github.mag0716.periodic D/PeriodicWork: doWork finish!!
2018-08-30 22:32:16.966 24260-24260/com.github.mag0716.periodic D/PeriodicWork: observe : status = WorkStatus{mId='8d4f2e0a-4ce7-4b4c-8513-552920c84740', mState=ENQUEUED, mOutputData=androidx.work.Data@0, mTags=[com.github.mag0716.common.LoggingWorker]}
```

## Release Notes

### [2.0.1](https://developer.android.com/jetpack/androidx/releases/work#2.0.1)

### [2.0.1-rc01](https://developer.android.com/jetpack/androidx/releases/work#2.0.1-rc01)

### [2.0.0](https://developer.android.com/jetpack/androidx/releases/work#2.0.0)

### [2.0.0-rc01](https://developer.android.com/jetpack/androidx/releases/work#2.0.0-rc01)

### [1.0.1](https://developer.android.com/jetpack/androidx/releases/work#1.0.1)

* 1.0.1-rc01 をバージョン名のみ変更

### [1.0.1-rc01](https://developer.android.com/jetpack/androidx/releases/work#1.0.1-rc01)

* Bug Fixes のみ

### [1.0.0](https://developer.android.com/jetpack/androidx/releases/work#1.0.0)

* 1.0.0-rc02 をバージョン名のみ変更

### [1.0.0-rc02](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-rc02)

* Bug Fixes のみ
  * アプリがクラッシュした後も Worker が正常に動作するようになった

### [1.0.0-rc01](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-rc01)

* Bug Fixes のみ

### [1.0.0-beta05](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-beta05)

* Bug Fixes のみ

### [1.0.0-beta04](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-beta04)

* Bug Fixes のみ
  * `exported="false"` のつけ忘れ修正

### [1.0.0-beta03](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-beta03)

* Bug Fixes のみ

### [1.0.0-beta02](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-beta02)

* Bug Fixes のみ

### [1.0.0-beta01](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-beta01)

* Bug Fixes のみ

### [1.0.0-alpha13](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-alpha13)

* [Breaking Changes] `Result` が `ListenableWorker` の inner class になった

### [1.0.0-alpha12](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-alpha12)

* [API Changes] `RxWorker` が追加
* [API Changes] Firebase JobDispatcher サポートが削除された
* [Breaking Changes] `Palyload` が　 `Result` に組み込まれた
* [API Changes] `Constraints.Builder.setTriggerContentMaxDelay`, `Constraints.Builder.setTriggerContentUpdateDelay` が追加された
* [API Changes] API 26 以上で使える `WorkerRequest.Builder.setBackoffCriteria` が追加された
* [Breaking Changes] `ContentUriTriggers` が public でなくなった
* [Breaking Changes] `WorkManager`, `WorkContinuation`, `OneTimeWorkRequest` の API で引数が varargs なメソッドが削除された
* [Breaking Changes] `WorkContinuation.combine` が削除された
* [Breaking Changes] `Operation.await()`, `ListenableFuture.await()` が追加された
* [Breaking CHanges] `Operation.getException()` が `Operation.getThrowable()` にリネーム


### [1.0.0-alpha11](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-alpha11)

* [API Changes] `CoroutineWorker` が追加
* [Breaking Changes] `WorkStatus` が `WorkInfo` にリネームされ、`getStatus` が `getWorkInfo` にリネームされた
* [Breaking Changes] `ListenableWorker.onStopped` の引数がなくなった
* [Breaking Changes] `androidx.work.test` が `androidx.work.testing` にリネーム
* [Breaking Changes] `Constraints` の public な setter がなくなった
* [Breaking Changes] `WorkerParameters.getTriggeredContentUris()`, `WorkerParameters.getTriggeredContentAuthorities()` の戻り値が Array から Collection に変わった
* [Breaking Changes] `ListenableWorker.onStartWork()` が `ListenableWorker.startWork()` にリネーム
* [Breaking Changes] `WorkStatus` のコンストラクタの一部がなくなった
* [Breaking Changes] `Configuration.getMaxJobSchedulerID`, `Configuration.getMinJobSchedulerID` の `ID` が `Id` にリネーム
* [API Changes] 多くの API に `@NonNull` が追加された
* [API Changes] `WorkManager.enqueueUniqueWork` が追加
* [Breaking Changes] `enqueue`, `cancel` が `Operation` を返すようになった
* [Breaking Changes] `enqueue` は `WorkRequest` の引数を受けつけなくなった
* [Breaking Changes] `WorkManager` を複数初期化すると `IllegalStateException` が発生するようになった

### [1.0.0-alpha10](https://developer.android.com/jetpack/androidx/releases/work#1.0.0-alpha10)

* [Breaking Change] Worker のデフォルトコンストラクタなどの deprecated なメソッドやクラスが削除された
* [Breaking Change] `NonBlockingWorker` が `ListenableWorker` にリネーム
* [Breaking Change] `WorkerFactory`, `DefaultWorkerFactory` が `WorkerFactory` にマージされた
* [Breaking Change] `WorkManager.synchronous()`, `WorkContinuation.synchronous()` が削除され、`ListenableFuture` を返却するメソッドが追加された
* [Bug Fixes] 1.0.0-alpha09 で発生した、`androidx-annotations.pro` の重複が解消された
