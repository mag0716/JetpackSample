# Lifecycle and side-effects

https://developer.android.com/jetpack/compose/lifecycle

## Lifecycle of a composable

[Managing state documentation](https://developer.android.com/jetpack/compose/lifecycle)で記載されている通り、CompositionはアプリのUIの記述であり、Composableの実行によって生成される。CompositionはComposableツリー構造である。

Jetpack ComposeがComposableを初めて起動する時、Composableを追跡を保持する。それから、アプリのstateが変更された時、Jetpack Composeはrecompositionを予約する。

Compositionはinitial compositionとrecompositionによる更新によってのみ生成され、Compositionの変更はrecompositionが唯一の手段である。

Key Point： ComposableのライフサイクルはCompositionの開始、終了と0回以上のrecomposition

recompositionは通常`State<T>`の変化によって引き起こされる。Composeはこれらを追跡し、`State<T>`を参照している全てのcomposableを実行する。呼び出されたcomposableはスキップすることができない。

Note： composableのライフサイクルはView,Activity,Fragmentよりもシンプルであり、composableが複雑なライフサイクルを持った外部のリソースを管理、操作する必要が出てきた場合は、Effects APIを利用するべき。

## Anatomy of a composable in Composition

Composition内のcomposableのインスタンスは呼び出された場所によって識別される。Composeのコンパイラは呼び出された場所毎に別のインスタンスとみなす。

Key Term： call siteとはcomposableが呼び出されたソースコードの場所のこと。

recompositionでcomposableが前回のcompositionとは異なるcomposableを呼び出した場合、呼び出されたかどうかを判別し、呼び出されたcomposableについては入力が変更されていなければrecompostionは回避される。

### Add extra information to help smart recompositions

compsableが複数回呼び出されるとCompositionには呼び出された回数だけ追加される。複数回呼び出しが同じ呼び出し場所の場合、Composeはユニークな情報を持っていないので、呼び出された順番を利用して、それぞれのインスタンスを判別する。

複数のデータを`for`を使ってcomposableを呼び出すケースでデータの末尾に追加されるケースでは、すでに生成されたcomposableはrecompositionされないが、データの先頭や途中に追加されたり削除やソートされてしまうと、すでに生成されていたcomposableについてもrecompositionされてしまう。
この動作は `key` を利用し、データをユニークに判別するためのIDを渡すことで防ぐことができる。

Key Point： `key` は composableインスタンスの特定の手助けになり、同じ呼び出し場所から複数のcomposalbeが生成され、副作用や内部状態を含む場合に重要となる。

`LazyColumn`のようにいくつかのcomposalbeでは`key`を受け取れるようになっている。

### Skipping if the inputs haven't changed

Compositionにすでにcomposableがあるとき、すべての入力が安定していて変更されていなければrecompotionをスキップすることができる。

安定しているとは以下を遵守している必要がある。

* `equals`の結果がいつでも同じとなる
* 型のpublicなプロパティが変更された場合は、Compotionに通知される
* すべてのpublicなプロパティも同様に安定している

これらはいくつかの共通な型があり、`@Stable`を利用して明示的に安定型としていなくてもコンパイラは安定型として扱う。

* `Boolean`, `Int` などのプリミティブ型
* `String`
* 関数型

これらの型はイミュータブルなので安定型となることができる。イミュータブルな型は変更されないのでCompotionに変更を通知されることがなく、簡単にこの制約に従うことができる。

Note： 全てのイミュータブルな型は安定型とみなすことができる

安定しているがミュータブルな型として`MutableState`がある。`MutableState`で保持されている場合、`value`プロパティに変更があった場合にはComposeに通知されるためstateオブジェクト全体が安定しているとみなすことができる。

Key Point： Composeは全ての入力が安定し変更されていない場合はrecompotionをスキップする。

Composeは肩が安定していることを証明できる場合のみ安定しているとみなす。例えば、インターフェースや実装あ普遍である可能性のある変更可能なpublicなプロパティを持つ方も安定していないものとして扱われる

強制的に安定しているものとして扱いたい場合は`@Stable`を利用する。

Key Point： Composeが型の安定性を推測できない場合は`@Stable`を利用することで、Composeがスマートなrecompotionを行えるようにする。

## State and effect use cases

アプリのstateを変更する必要がある場合、Effects APIを利用して、副作用が予測可能な方法で実行されるようにする必要がある。

Key Term： Effects APIとはUIを出さずにCompotionが完了した時に副作用を実行する関数のこと。

Composeでは様々なEffects APIがあるため簡単に使い過ぎてしまうことができる。Effects APIで行う作業はUIに関連したものであり、unidirectional data flowが壊れないように注意する必要がある。

Note： レスポンシブUIは本質的には非同期だが、Jetpack Composeではコールバックを使用する代わりにCoroutinesを利用することでこれを解決している。

### LaunchedEffect: run suspend functions in the scope of a composable

composable内でsuspend関数を呼び出すために`LaunchedEffect`を利用する。`LaunchedEffect`が始まるとパラメータとして渡されたコードブロックがCoroutinesとして起動され、`launchedEffect`から去るとキャンセルされる。`LaunchedEffect`が異なるkeyでrecomposeされると、すでに起動されたCoroutineはキャンセルされ新たにCoroutineが起動する。

例：`SnackbarHostState.showSnackbar`

### rememberCoroutineScope: obtain a composition-aware scope to launch a coroutine outside a composable

`LaunchedEffect`はcomposable関数なので、composable関数内からしか利用することができない。composable関数外から起動したいが、compositionから去った時に自動的にキャンセルしたい場合は`rememberCoroutineScope`を利用する。Coroutineを手動でコントロールする必要があるケースでも利用する。

### rememberUpdatedState: reference a value in an effect that shouldn't restart if the value changes

`LaunchedEffect`はkeyであるパラメータが変更された時に再起動される。値が変化しても再起動されない値をキャプチャしたいケースでは`rememberUpdateState`を利用する。

例えば、表示されてしばらく消えるUIはrecomposeされても再度表示されるべきではないというケース。

呼び出された場所のライフサイクルと一致させるために`Unit`や`true`のような決して変化しない定数を利用し、`LaunchedEffect(true)`などと利用する。

Warning： `LaunchedEffect(true)`は`while(true)`と同じように本当に使うべきなケースなのかを考える必要がある。

### DisposableEffect: effects that require cleanup

keyの変更やcomposableがCompositionから去った時に副作用のためにクリーンアップする必要があるケースでは`DisposableEffect`を利用する。`DisposableEffect`のkeyが変更すると、composableは自身の現在のeffectを処分し、effectの際呼び出しによってリセットする必要がある。

例：`BackHandler`の `OnBackPressedCallback`, `OnBackPressedDispatcher`

Note： `onDispose`が空のブロックになるのは良いプラクティスではないので該当するケースがあったら再検討する。

### SideEffect: publish Compose state to non-compose code

Composeによって管理されていないオブジェクトと一緒にstateを共有し、recomposition毎に毎回実行されるようにするためには`SideEffect`を利用する。

例： `BackHandler`の`enabled`

### produceState: convert non-Compose state into Compose state

`produceStaet`は`State`を返却することができるCompsitionのためにCoroutineスコープを起動する。non-Compose stateからCompose stateへの変換に利用され、例えば`Flow`, `LiveData`, `RxJava` で利用する。

`produceState`はCompositionに入った時に起動され、去った時にキャンセルされる。同じ値のStateが返却された場合はrecompositionはトリガーされない。

Note： 値が返却されるcomposable関数は小文字始まりの命名にするべき。

Key Point： 内部的には`produceState`は他のeffectを利用する。`remember { mutableStateOf(initialValue) }`を利用して、結果を保持し、`LaunchedEffect`の`producer`ブロックをトリガーする。`producer`ブロックで
`value`が更新されるたびにけっkがの状態は新しい値に更新される。

この仕組みを使えば既存のAPI常に独自のeffectを簡単に作成することができる。

### derivedStateOf: convert one or multiple state objects into another state

あるstateが計算されたり、他のstateから派生する場合に`derivedStateOf`が利用され、計算に使用するstateのいずれかが変化した場合のみ計算が行われることが保証される。

例：TODOリストの優先度の変化によるUIの変更

### snapshotFlow: convert Compose's State into Flows

`snapshotFlow`を利用することで`State<T>`をcold Flowに変更することができる。`Flow.distinctUntilChanged`のような動作となる。

`Flow`に変換されることで、`Flow`のオペレーターを利用できるという利点が生まれる。

## Restarting effects

`LaunchedEffect`, `produceState`, `DisposableEffect`のようないくつかのeffectは引数としてkeyを複数受け取ることができ、実行中のeffectのキャンセルや新しいkeyでの実行に利用される。

この動作に微妙な違いがあるためeffectの再起動に使用したパラメータが適切でないと以下のような問題を引き起こす可能性がある。

* effectの再起動が必要以上に少ないとバグが発生する可能性がある
* effectの再起動が必要以上だと効率が悪くなる

目安として、effectのコードブロックで利用しているミュータブル、イミュータブルの変数はeffect composableのパラメータとして追加する必要がある。変数の変更によってeffectを再起動させたくない場合は`rememberUpdateState`でラップする。keyのない`remember`でラップしている変数については変化しないので、effectのkeyとして渡す必要はない。

Key Point： effectで利用している変数はパラメータとして渡すか、`rememberUpdateState`を使うかのいずれかの対応が必要。

### Constants as keys

effectのkeyとして`true`のような定数を利用する場合は呼び出し場所のライフサイクルに従う必要がある。
