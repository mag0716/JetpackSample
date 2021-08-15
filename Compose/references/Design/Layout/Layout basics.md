# Layouts in Compose

https://developer.android.com/jetpack/compose/layouts/basics

## Goals of layouts in Compose

レイアウトシステムのJetpack Composeの実装には2つの主目的がある。高パフォーマンスになることとカスタムレイアウトを容易に実装できるようになることである。Composeでは子供のレイアウトの測定は複数回を禁止することで高パフォーマンスを実現している。複数回の計測が必要な場合は、`intrinsic measurements` が用意されている。詳細は https://developer.android.com/jetpack/compose/layouts/intrinsic-measurements

Note: Android Viewシステムでは`RelativeLayout`などでViewをネスとすることでのパフォーマンス問題に直面することがある。Composeは複数回の計測を避けることで、パフォーマンスの影響なしにネストが可能になる。

## Basics of Composable functions

Composable関数はComposeの基本構成ブロックであり、UIの一部を記述し、`Unit`を返却する関数である。この関数はいくつかの入力を受け、画面上に何を表示するのかを生成する。詳細は https://developer.android.com/jetpack/compose/mental-model

Composable関数はいくつかのUI要素を返すがどのように配置するのかを指示しないと思うように配置されないかもしれない。

どのように配置するのかを指示しないと、UI要素は重なって表示される。

ComposeはUI要素の配置に役立つ、すぐに使えるレイアウトのコレクションを提供し、より専門的な独自のレイアウトを簡単に定義することができる。

## Standard layout components

多くのケースでは、[Components's standard layout elements](https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/package-summary) を利用することができる。

`Column` は縦に要素を配置することができ、`Row` は横に配置することができる。
`Box` は他の要素をある要素の上に配置することができる。

Note: Composeはネストされたレイアウトを効率的に扱うので複雑なUIをデザインするのに適しており、パフォーマンスを理由にネスとされたレイアウトを避ける必要があるAndroid Viewsよりも改善されている。

`Row`の子供の要素の位置を変更する場合は、`horizontalArrangement`, `verticalAlignment` を使い、`Column` では、`verticalArrangement`, `horizontalAlignment` を使う。

## Modifier

`Modifier`は以下のようなComposableの装飾や拡張をすることができる。

* サイズ、レイアウト、振る舞いや外観を変更する
* アクセシビリティのラベルのような情報を追加する
* ユーザーの入力を処理する
* 要素のクリック、スクロール、ドラッグ、ズームのような高レベルな操作を追加する

`Modifier`は標準のKotlinのobjectであり、`Modifier`クラスを呼び出すことで生成される。メソッドチェインも可能。

* `clickable`:  ユーザーの入力に反応しリップルエフェクトが表示される
* `padding`: 要素の周りにスペースを配置する
* `fillMaxWidth`: 親のサイズに合わせて幅を決定する
* `size()`: 指定したwidth, heightを指定する

Note: ModifierはAndroid Viewでのレイアウトのパラメータに似た役割を果たすが、Modifierはスコープが指定されているため肩の安全性を提供し、何が利用可能なのかの発見や理解に役立つ。

### Order of modifiers matters

Modifierの順番にも意味があり、前に返されたものを変化するので最終的な結果に影響を与える。

例えば、`clickable`の後に`padding`が指定されているとパディング部分にもリップルエフェクトが走るが、`padding`を先にするとパディング部分にはリップルエフェクトが走らないようになる。

Note: 明示的な順番は異なるModifierがどのように作業するのかを推論するのに役立つ。Android Viewだとマージンは外側に作用し、パディングは内側に作用するが、Modifierではこの動作を明示的にかつ予測可能にすることができる。Modifierにマージンがなくパディングのみ存在している理由もこのため。

### Build-in modifiers

Jetpack ComposeではComposableを装飾したり拡張するための組み込みのModifierの一覧が用意されている。

#### size

`size`はComposableのサイズをセットすることができる。(デフォルトでは子供の要素のサイズとなる)

ただし、`size` は親のレイアウトによる制約によっては指定したサイズを満たせない可能性があることに注意が必要。もし、親の制約を無視して固定のサイズを指定する必要がある場合には`requiredSize`を利用する。

Note: レイアウトは一般的に親から渡される制約に基づいており、子はその制約を尊重しなければならない。しかし、UIが要求するものとは限らず、この動作を回避する方法が存在する。例えば、`requiredSize`のような`Modifier`を直接渡して、親から子が受け取る制約を上書きしたり、異なる動作をするカスタムレイアウトを使用することができる。子が制約を守らない場合、レイアウトシステムは親からそのことを隠し、子のサイズが親のサイズと合わせられることで強制されるように見える。レイアウトシステムは制約に従っていると仮定して親が割り当てたスペース内でセンタリングする。開発者は`wrapContentSize`を利用することでセンタリングの動作を上書きすることができる。

親の有効なスペースを全て満たすようにレイアウトするには`fillMaxSize`(`fillMaxHeight`, `fillMaxWidth`)を利用する。

テキストのベースラインからの距離を指定したい場合は`paddingFromBaseline`を利用する。

#### Offset

レイアウトを本来の位置から相対的に配置するには`offset`を利用し、`x`, `y` をセットする。オフセットには正数だけでなく負数を指定することも可能。
`padding`と`offset`の違いはComposableのサイズが変わるかどうか。

`offset`はLTR, RTLによって適用される方向が変わるので、必ず同じ方向にずらしたい場合は`absoluteOffset`を利用する。

### Type safety in Compose

Composeでは特定の子供のみで動作するModifierが存在する。例えば、`Box`に同じサイズの子供を追加する場合、`matchParentSize`を利用するが、`BoxScope`内でしか利用できない。

Note: Android Viewシステムはタイプセーフではない。開発者は特定の親のコンテキストに従って必要なパラメータを探す必要などがあった。

スコープされたModifierは親が子について知っておくべき情報を親に通知する。これらは一般的に`parent data modifiers`と呼ばれているが一般的な`Modifier`と使用上の違いはない。

#### matchParentSize in Box

子供の要素を`Box`と同じサイズにしたい場合、`matchParentSize`を利用する。

`matchParentSize`は`Box`スコープのみ有効で`Box`の子供として追加した要素にしか利用できない。

`matchParentSize`の代わりに`fillMaxSize`を利用すると親の`Box`のサイズも可能なサイズまで広げられてしまう。

#### weight in Row and Column

Composableのサイズはデフォルトでは自身がラップするコンテンツによってサイズが決定する。
Composableのサイズを自身の親によって柔軟にサイズが変わるようにするためには`weight`を利用する。
`weight`は`RowScope`か`ColumnScope`でのみ利用可能。

### Scrollable layouts

https://developer.android.com/jetpack/compose/gestures を参照。

Lazyリストについては https://developer.android.com/jetpack/compose/lists#lazy を参照。

### Responsive layouts

レイアウトは画面の向きや端末自体のサイズなどを考慮したデザインとなっているべき。Composeではいろいろな画面構成に適用するための仕組みが用意されている。

#### Constraints

`BoxWithConstraints`を使うことで親からの制約を知り、それに応じたレイアウトを設計することができる。
`BoxWithConstraints`はラムダで指定したコンテンツ内でサイズを知ることができ、画面構成によってレイアウトを変更することができる。

### Slot-based layouts

Composeは`androidx.compose.material:material`によってMaterial Designをベースとした`Drawer`, `FloatingActionButton`, `TopAppBar` などのComposableを提供し、UIの構築を容易にしている。

Material ComponentsはComposableの上にカスタマイズのレイヤーを追加するslot APIsを多用している。
このアプローチによってコンポーネントは子要素のすべての設定パラメータを公開するのではなく、自分自身を設定できる子要素を受け入れることでより柔軟になっている。Slotは開発者が必要に応じて埋められるように空のスペースを残している。
例えば、`TopAppBar`の場合は、ナビゲーションアイコン、タイトル、メニュー

Composableは通常`content`を受け取るが、Slot APIsでは複数の`content`が指定できるようになっている。
例えば、`TopAppBar`の場合は、`title`, `navigationIcon`, `actions`

`Scaffold`は基本的なMaterial Designのレイアウトの構成を実装することができ、`TopAppBar`, `BottomAppBar`, `FloatingActionButton`, `Drawer` などのスロットを提供しているので、簡単にこれらのコンポーネントを正しい位置に配置することができる。
