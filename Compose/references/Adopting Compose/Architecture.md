## Architecting your Compose UI

https://developer.android.com/jetpack/compose/architecture

ComposeではUIはイミュータブルで描画後に更新する手段はなく、UIを更新するにはStateを更新する必要がある。

Composable関数は状態とイベントを公開するので、Unidirectional data flow patternにフィットしている。
このリファレンスでは、unidirectional data flow patternの実装方法、イベントと状態の保持の仕方、ViewModelをどう利用するのかについてフォーカスしている。

Note: データレイヤーやビジネスレイヤーなどの他のレイヤーはJetpack Composeを採用しても影響を受けない。
Androidアプリでのアーキテクチャについては https://developer.android.com/jetpack/guide を参照。

### Unidirectional data flow

Unidirectional data flowは状態が下に流れ、イベントが上に流れる設計パターンで、UIに状態を表示するComposableを状態を保存、変更する部分から分離することができる。

Unidirectional data flowに従ったアプリでのUI更新は以下のようになる

* Event：ボタンクリックでの処理などUIの一部がイベントを生成して上に渡す。もしくは、アプリの他のレイヤからイベントが渡される
* Update state：イベントハンドラーがStateを更新する
* Display state：Stateホルダーが下に渡しUIが更新される

Jetpack Composeではこの設計パターンに従うことでいくつかの利点が生まれる。

* Testability：UIからStateを分離することでいずれのテストも容易になる
* State encapsulation：Stateは1箇所の更新だけでよいので状態不正による不具合が発生しづらい
* Ui consistency：全てのStateは即座にUIに反映される

#### Unidirectional data flow in Jetpack Compose

ComposeはStateとイベントをベースに動作している。
ComposeはデータホルダーとしてStateを定義し、Stateの変化によってrecompositionが発生する。
`remember`, `rememberSaveable` を利用することで値を必要な期間保持することができる。

`TextField`には`String`で渡せるのでどのような場所で定義されたデータを利用できる。
`onValueChanged`で値を変化させる必要がある場合は、`State`を保持するようにする必要がある。

Key Points：
`mutableStateOf(value)`：ComposeでObservableとして利用できる`MutableState`を生成する。値を読み込みしているComposableは値が変化したらrecompositionが発生する

` remember`：compositionにオブジェクトを保存し、compositionが削除されるまでオブジェクトを保持する

` rememberSaveable`：`Bundle`内に保存することでconfiguration changedが発生してもStateを維持する

Note： state hoisting については https://developer.android.com/jetpack/compose/state を参照

#### Define composable parameters

ComposableにStateを定義する時、以下に気を付けるべき

* そのComposableはどの程度再利用可能か？
* Stateの値がこのComposableのパフォーマンスにどのように影響する？

分離と再利用を促進するため、Composableで保持する情報は可能な限り少なくする必要がある。
例えば、ニュース記事のヘッダーを保持するComposableの場合はニュース記事全体ではなく表示する必要がある情報のみを渡す。

個別のパラメータを使用するとパフォーマンスが向上することもある。
例えば、ニュース記事全体を渡していると表示する情報以外が変わった時でもrecompositionされてしまう。

受け渡すパラメータの数は慎重に検討する必要があり、数が多すぎると可読性が低下するので、その場合は1つのクラスにまとめることが推奨。

### Events in Compose

タップ、テキスト変更、タイマーなど全ての入力はイベントとして表現すべき。
これらのイベントはUIの状態を変更するので、ViewModelが1箇所でこれらの更新を扱うべき。

一貫性を壊し不具合を作り出す可能性があるのでUIレイヤーはイベントハンドラー外からはStateを変更してはならない。

Stateのためにイミュータブルな値とイベントハンドラーのラムダを渡すことが好まれ、このアプローチには以下の利点がある。

* 再利用性を改善できる
* Stateを直接変更しないことが保証されている
* 他のスレッドから変更されることがないので並行性による問題を避けられる
* コードの複雑性を減らせる

例えば、`String`とラムダをパラメータとして受け取るComposableは多くのコンテキストから呼び出すことができ再利用性が高い。

#### ViewModels, states, and events: an example

`mutableStateOf` APIに加えて、Composeは`LiveData`, `Flow`, `Observable`のextensionsを提供している。

`ViewModel`と`LiveData`を使い以下のいずれかに従うと、unidirectinal data flowを表現することが可能になる。

* `LiveData`によってUIの状態が公開されている
* `ViewModel`がUIまたは他のレイヤからのイベントを処理し、イベントに基づいて状態ホルダーを更新する
