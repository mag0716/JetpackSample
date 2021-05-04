# State and Jetpack Compose

https://developer.android.com/jetpack/compose/state

Key Term：アプリにおけるStateとは時間の経過とともに変化する可能性のある値のこと。例えば、データベースに保存されている値、プロパティ、加速度の現在の値など

Jetpack Composeでは、AndroidアプリでStateを保存、使用する場所と方法を明確にすることができる。この資料ではStateとComposableの関係とStateをより簡単に扱うために提供されたAPIについて説明する。

## State in Compose

ComposeのコアとなっているのがStateという概念。

`OutlinedTextField`の`value`に空文字を指定すると、ユーザが入力してもテキストに変化がおきない。
これはComposeにおけるCompositionとrecompositionの仕組みによるもの。

### Composition and recomposition

compositionはUIを記述しComposableを実行することで生成される。CompositionはUIを記述するComposableをツリー構造にしたもの。

initial compositionの時、Composeは呼び出したComposableを記録する。その後、アプリのStateが変化するとrecompositionをスケジュールする。recompositionはStateの変化に応じて変更された可能性のあるComposableを実行し、変更を反映するためにcompositionを更新する。

compositionはinitial compositionのみで生成され、recompositionによって更新される。更新はrecompositionが唯一の方法。

Key Term：
  Composition：Composableを実行する時に構築されるUIの記述
  Initial composition：Composableの初回の実行で生成されるComposition
  Recomposition：データ変更時にCompositionを更新するために行うComposableの再実行

### Introducing state

Composableを更新するには、`TextField`のStateを表す値を渡し、`TextField`の値が変化した時にStateを更新するコードが必要となる。

テキストを保持するStateを導入するには`remember { mutableStateOf() }`を使い、`value`にStateを、`onValueChanged`にStateを更新する処理を追加する。

`remember`を利用することによってComposable関数はメモリ上にオブジェクトを保存することが可能となる。`remember`で計算された値はinitial composition時にcomposition内に格納され、recomposition時に格納された値が返却される。

Key Term：
  `remember`：別のComposable関数にメモリを与える関数

Note：
  `remember`はCompositionにオブジェクトを格納し、`remember`を呼び出したComposableがCompositionから削除されるとオブジェクトを破棄する。

`mutableStateOf`はComposeにおけるObservableな型である`MutableState`を生成する。値が変化するとこの値を読み取っているComposable関数のrecompositionがスケジュールされる。

`remember`はrecompositionを超えたStateを管理を手助けする。`mutableStateOf`を`remember`なしで利用すると、Stateは毎回初期化されるので同じ表示となる。

`remember`で管理している値を`if`などのロジックに利用することも可能で値によって表示するUIを変えることもできる。

`remember`はrecompositionを超えたStateの維持はするがConfigurationの変化は維持しないので、`Bundle`に自動的に保存してくれる`rememberSaveable` を利用する必要がある。


Key Point：Composableに内部Stateを追加するさいにConfigurationの変更に対応するべきかを考慮し、必要があれば`rememberSaveable`を利用してStateを保存する。

### Stateless composables

Composable関数が自身でStateを保持してしまうと再利用やテストが困難になるので、stateless composableにするべき。

Stateless composableにするために、state hoistingを利用する。State hoistingはStateをComposableの呼び出し元に移動させるプログラミングパターンのことで、Stateを引数として置き換えることで対応する。

Key Term：
  State hoistingはStateを上部のツリーに移動させることでStatelessにすること。
  Composableに適用する時は以下をよく利用することになる。
    `value`：現在の値
    `onValueChanged: (T) -> Unit`：値変化時のイベント

ComposableをStateの保存方法から切り離すことで、Composableの利用元が変更されてもComposableの実装方法を変更する必要がなくなる。

Stateを呼び出し先に渡し、イベントを呼び出し元に返すパターンはunidirectional data flowと呼ばれ、UIにStateを表示するComposableとStateを保存、変更する部分を切り離すことができる。

### ViewModel and state

Jetpack Composeでは`ViewModel`を利用して`LiveData`や`Flow`などのObservableのStateを後悔し、Stateに影響を与えるイベントを処理することができる。

`observeAsState`は`LiveData<T>`を観測し、Jetpack Composeが直接扱える`State<T>`を返す。`observeAsState`はcompositionの間だけ`LiveData`を観測する。

Note: `by`(property delegate)を利用することで`State<T>`を`<T>`として扱うことができる

`ViewModel`を利用したunidirectional data flowの詳細な実装方法ついては[Architecting your Compose UI](https://developer.android.com/jetpack/compose/architecture)を参照。

## Using remember

Key Term: `remember`はComposable関数にメモリを与える

Note：
  `remember`はCompositionにオブジェクトを格納し、`remember`を呼び出したComposableがCompositionから削除されるとオブジェクトを破棄する。

### Use remember to store immutable values

フォーマットされたテキストなど、UI操作をキャッシュする時にイミュータブルな値を保存することができる。

Key Point：イミュータブルな値は決して変化しない値のこと

#### Use remember to create internal state in a composable

Key Point：ミュータブルオブジェクトは変更可能なオブジェクトのこと

`remember`を使ってミュータブルなオブジェクトを保存する時、ComposablesにStateを追加する。

Composableが使用する全てのミュータブルなStateはObservableであることが強く推奨される。こうすることで、Stateが変更されるたびにComposeは自動的にrecompostionすることが可能となる。

Note：
  `remember`がミュータブルオブジェクトを保持している場合、ComposableはStatefulになる。

  Composableの内部Stateはクラスの`private var`と同じで呼び出し側が操作する必要のない実装に利用されるべきで、呼び出し側がStateの読み書きが必要な場合はstate hoistingを利用する。

Composableで`MutableState`の定義する方法は以下の3種類

* `val mutableState = remember { mutableStateOf(default) }`
* `var value by remember { mutableStateOf(default) }`
* `val (value, setValue) = remember { mutableStateOf(default) }`

これらは全て同等でシンタックスシュガーとして提供されているだけなので、読みやすいものを選択すれば良い。

Note： `MutableState<T>`を生成する時は`remember`が重要。つけ忘れるとrecompositionごとに初期化される。

内部Stateを別のComposableのパラメータとして利用することも可能。

#### Modify internal state in a composable

StateはイベントではなくComposableの実行中にStateを変更した場合、副作用となるのでComposableのイベントによって変更されるべき。副作用については[Thinking in Compose](https://developer.android.com/jetpack/compose/mental-model)を参照。

Key Point：StateはComposable内のイベントによって変更するべき。

### Use other types of state in Jetpack Compose

Jetpack ComposeではStateの保持に`MutableState<T>`を利用することは必須ではなく、他のObservableな肩をサポートしている。Stateの変化によって自動的にrecompositionが発生するので`State<T>`に変換するべき。

Composeでは以下のObservable型を`State<T>`に変換する関数が用意されている。

* `LiveData`
* `Flow`
* `RxJava2`

Key Point：
  Composeは`State<T>`を読み込んでいると自動的にrecompositionが発生する。

  `LiveData`などのObservableな肩を使っている場合、Composableが読み込む前に`LiveData<T>.observeAsState()`のような拡張関数で`State<T>`に変換するべき。

Caution：
  `ArrayList<T>`, `mutableListOf()`のようなミュータブルオブジェクトをStateとして利用すると不正なデータが表示される可能性がある。

  ミュータブルオブジェクトはObservableではないので変化した時にrecompositionは発生しない。

  non-observableなミュータブルオブジェクトを利用する際は`State<List<T>`のようなObservableなデータホルダーを利用することが推奨される。

### Separate internal state from UI composables

再利用可能なComposableを開発していると同じComposableのStateful版とStateless版を公開したくなるケースがある。

Stateful版とStateless版を公開する場合、state hoistingを利用し、UIを表示するStatelessなComposableを抽出する。

state hoisting では、`value: T`, `onValueChange: (T) -> Unit`をパラメータとして扱うケースが多いが、`onValueChange`が1つである必要はない。

hoistingされたStateはいくつかの重要な特性がある。

* Single source of truth：Stateを複製するのではなく1つとなるようにすることでバグを避けるのに役立つ
* Encapsulated：StatefulなComposableのみが内部のStateを変更することができる
* Shareable：hoistingされたStateは他のComposableに共有することができる
* Interceptable：StatelessなComposableの呼び出し元はStateの変更前にStateを変更するかどうかを選択できる
* Decoupled：StatelessなComposableのためのStateはどこでも保存することができる

Key Point：
  hoisting stateをする時、以下のルールが存在する

  1. Stateは利用するComposableの最下位の共通の親にhoistされるべき
  2. Stateは変更される可能性がある最上位にhoistされるべき
  3. 2つのStateが同じイベントに反応して変更される場合、一緒にhoistされるべき

  これらに従わないと、unidirectional data flowに従うことが難しくなったり、不可能になる

### Restore UI state after activity or process recreation

`rememberSaveable`はActivityやプロセスの再生生後にUIの状態を復元する。

`Bundle`に自動的に追加されるので、`Bundle`に追加できないデータについてはいくつかの解決策がある。

一番簡単なのは`@Parcelize`を追加すること。

`@Parcelize`が適さない場合、`mapSaver`を定義して、`rememberSaveable`のパラメータに渡す。

`Map`のkeyの定義を避ける場合は、`listSaver`を利用しindexを使う。

### Learn more

[Using State in Jetpack Compose codelab](https://developer.android.com/codelabs/jetpack-compose-state)を参照。
