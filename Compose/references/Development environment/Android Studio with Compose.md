# Use Android Studio with Jetpack Compose

https://developer.android.com/jetpack/compose/setup

Jetpack Composeを使った開発体験をよくするベストな方法は Android Studio Arctic Foxの最新バージョンを利用すること。

## Try Jetpack Compose sample apps

手っ取り早くJetpack Composeを試したかったら、[compose-samples](https://github.com/android/compose-samples)を動かしてみるとよい。

## Create a new app with support for Jetpack Compose

新たにプロジェクトを作成する場合は、`Empty Compose Activity` テンプレートを利用するとよい。

Note: Android Studioでの設定後、ビルドを促すバナーが表示されるかもしれないが、Jetpack Composeのプレビューを表示するのに必要。

## Add Jetpack Compose to an existing project

### Configure Kotlin

Kotlin 1.4.32 以上が必要

### Configure Gradle

`minSdkVersion` は21以上が必要

### Add Jetpack Compose toolkit dependencies

必要な依存関係を追加する
