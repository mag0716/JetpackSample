# Kotlin for Jetpack Compose

https://developer.android.com/jetpack/compose/kotlin

## Default arguments

大部分のComposeライブラリはdefault argumentsを利用しており同じComposableを実行するのが楽になる。

## Higher-order functions and lambda expressions

高階関数は通常lambdaとペアで利用され、一度しか使われない関数でも別に定義する必要はない。

## Trailing lambdas

trailing lambda syntax は Composeでも利用でき、`Column()` などでも `content` を外側の `{}` に定義することができる。
指定するパラメータが `content` のみの場合は `()` を書く必要はない。

## Scopes and receivers

いくつかのメソッドやプロパティは特定のスコープ内でしか利用できないので、不適切な利用を避けることができる。例えば、`Row`は`RowScope`が適用され、`align`が使えるようになる。

いくつかのAPIはreceiver scopeと呼ばれるlambdaを受け取れ、他の場所で定義されているプロパティと関数にアクセスすることができる。

## Delegated properties

ComposeではDelegated propertiesは`State`を扱うプロパティで特に有用。

Note: `remember`, `mutableStateOf`は[Managing state](https://developer.android.com/jetpack/compose/state)を参照。

## Destructuring data classes

`ConstraintLayout`の`createRefs()`

## Singleton objects

`MaterialTheme`はsingleton object

## Type-safe builders and DSLs

`LazyRow`と`LaxyColumn`などはDSLsを利用しており、指定の構造で実装する必要がある。

`Canvas`は`DrawScope`をレシーバーを使った関数をパラメータを受け取り、`DrawScope`で定義されたメンバー関数を呼び出せるようにしている。

## Kotlin Coroutines

Jetpack ComposeではUIレイヤーでCoroutinesを安全に利用するために`CoroutineScope`を返却する`rememberCoroutineScope`が提供されている。
`ScrollState#animateScrollTo` API で利用されている。
