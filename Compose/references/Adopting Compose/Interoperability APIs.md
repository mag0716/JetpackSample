# Interoperability APIs

https://developer.android.com/jetpack/compose/interop/interop-apis

## Compose in Android Views

ViewベースでデザインされたアプリにComposeベースのUIを追加することができる。

新たに作成する際は完全にComposeベースの画面とし、Activityで`setContent()`を呼び出しComposable関数を呼び出せば良い。

Caution: `ComponentActivity.setContent`を利用するには `androidx.activity:activity-compose:$latestVersion` への依存を追加する。

最新バージョンについては https://developer.android.com/jetpack/androidx/releases/activity を参照。

Fragmentや既存のViewにCompose UIを組み込みたい場合は`ComposeView`を利用し、自身で`setContent` を呼び出す。
`ComposeView`はViewベースの`View`であり、`ViewTreeLifecycleOwner`にアタッチする必要がある。
`ViewTreeLifecycleOwner`は構成を維持したままアタッチとで達を繰り返し行うことができる。
`ComponentActivity`, `FragmentActivity`, `AppCompatActivity` は全て `ViewTreeLifecycleOwner` を実装している。

`ComposeView`は他の`View`と同様にXMLレイアウトに定義することができる。

また全画面がComposeで作成されている場合は、`ComposeView`をFragmentに直接含めることができ、XMLレイアウトファイルの利用を避けることができる。

同じレイアウトに複数の `ComposeView` がある場合は、`savedInstanceState` のために一意のIDが必要になる。

## Android Views in Compose

Compose UIにViewベースのUIを含めることができ、まだComposeで有効になってない`AdView`, `MapView`などのUI要素を利用する時に役立つ。
このアプローチはカスタムViewを再利用するときにも使える。

View要素や階層を含めるには`AndroidView`を利用する。
`AndroidView`は`View`を返すラムダを渡す。
Viewがインフレートされた時にコールバックされる`update`も提供されており、読み込んでいる`State`が変化するたびにrecompositionされる。

Note: `AndroidView`の`viewBlock`で`View`の生成を行い、`AndroidView`外で`View`の参照や`remember`をしてはならない。

XMLレイアウトを埋め込む場合は`androidx.compose.ui:ui-viewbinding`によって提供される`AndroidViewBinding`を利用する。
この機能を利用するには ViewBindingを有効にする必要がある。

他のComposableと同じく、`AndroidView`にも`Modifier`パラメータを利用することができ、位置などを指定することができる。

## Calling the Android framework from Compose

ComposeはAndroidフレームワークのクラスと密接に結びついている。
`Activity`, `Fragment` でホストされているため`Context`が必要になり、システムリソース、`Service`, `BroadcastReceiver`などのクラスが必要になる場合もある。

システムリソースについては、https://developer.android.com/jetpack/compose/resources を参照。

### Compostion Locals

`CompositionLocal`を利用すると、Composable関数を通じて暗黙的にデータを渡すことができる。
通常UIツリーの特定のノードに値が設定され、Composable関数のパラメータとして定義しなくても、子供のComposable関数で利用することができる。

`CompositionLocal`にはAndroidフレームワークの値にアクセスするための`LocalContext`, `LocalConfiguration`, `LocalView` が用意されている。
IDEで検出しやすいように`Local`がプレフィックスとしてついている。

`CompositionLocal`の現在の値にアクセスするには`current`プロパティを利用する。

より詳細な例は、https://developer.android.com/jetpack/compose/interop/interop-apis#case-study-broadcastreceivers を参照。

## Other interactions

必要な操作のためにユーティリティが定義されていない場合は、Composeのガイドライに従ってデータは下に流れ、イベントは上に流れるようにすることが推奨される。
詳細は https://developer.android.com/jetpack/compose/mental-model を参照。

## Case Study: BroadcastReceivers

Composeで移行または実装する機能の例として`CompositionLocal`と副作用を紹介する。
ここでは、`BroadcastReceiver`をComposable関数から登録する必要がある。

このケースを実現するには、`LocalContext`, `rememberUpdatedState`, `DisposableEffect`を利用する。
