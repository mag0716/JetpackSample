# Lifecycle of composables

https://developer.android.com/jetpack/compose/lifecycle

## Lifecycle overview

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
* 型のpublicなプロパティが変更された場合は、Compositionに通知される
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
