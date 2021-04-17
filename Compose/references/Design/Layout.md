# Layouts in Compose

https://developer.android.com/jetpack/compose/layout

## Standard layout components

多くのケースでは、[Components's standard layout elements](https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/package-summary) を利用することができる。

`Column` は縦に要素を配置することができ、`Row` は横に配置することができる。
`Box` は他の要素をある要素の上に配置することができる。

Note: Composeはネストされたレイアウトを効率的に扱うので複雑なUIをデザインするのに適しており、パフォーマンスを理由にネスとされたレイアウトを避ける必要があるAndroid Viewsよりも改善されている。

`Row`の子供の要素の位置を変更する場合は、`horizontalArrangement`, `verticalAlignment` を使い、`Column` では、`verticalArrangement`, `horizontalAlignment` を使う。

## Modifier

Modifierは以下のようなComposableの装飾や拡張をすることができる。

* サイズ、レイアウト、振る舞いや外観を変更する
* アクセシビリティのラベルのような情報を追加する
* ユーザーの入力を処理する
* 要素のクリック、スクロール、ドラッグ、ズームのような高レベルな操作を追加する

Note: ModifierはAndroid Viewでのレイアウトのパラメータに似た役割を果たすが、Modifierはスコープが指定されているため肩の安全性を提供し、何が利用可能なのかの発見や理解に役立つ。

Modifierの順番にも意味があり、前に返されたものを変化するので最終的な結果に影響を与える。

例えば、`clickable`の後に`padding`が指定されているとパディング部分にもリップルエフェクトが走るが、`padding`を先にするとパディング部分にはリップルエフェクトが走らないようになる。

Note: 明示的な順番は異なるModifierがどのように作業するのかを推論するのに役立つ。Android Viewだとマージンは外側に作用し、パディングは内側に作用するが、Modifierではこの動作を明示的にかつ予測可能にすることができる。Modifierにマージンがなくパディングのみ存在している理由もこのため。

### Build-in modifiers

Jetpack ComposeではComposableを装飾したり拡張するための組み込みのModifierの一覧が用意されている。

#### Padding and size

`padding`はComposableの周辺にパディングをセットする。

`size`はComposableのサイズをセットすることができる。(デフォルトでは子供の要素のサイズとなる)
ただし、`size` は親のレイアウトによる制約によっては指定したサイズを満たせない可能性があることに注意が必要。もし、親の制約を無視して固定のサイズを指定する必要がある場合には`requiredSize`を利用する。

親の有効なスペースを全て満たすようにレイアウトするには`fillMaxSize`(`fillMaxHeight`, `fillMaxWidth`)を利用する。

`Box`のサイズに影響を与えずに`Box`のサイズと同じ子供の要素を追加したい場合は、`matchParentSize`を利用する。
`matchParentSize`は`Box`のスコープ内でのみ有効である。

テキストのベースラインからの距離を指定したい場合は`paddingFromBaseline`を利用する。

#### Offset

レイアウトを本来の位置から相対的に配置するには`offset`を利用し、`x`, `y` をセットする。オフセットには正数だけでなく負数を指定することも可能。
`padding`と`offset`の違いはComposableのサイズが変わるかどうか。

`offset`はLTR, RTLによって適用される方向が変わるので、必ず同じ方向にずらしたい場合は`absoluteOffset`を利用する。

### Scrollable layouts

https://developer.android.com/jetpack/compose/gestures を参照。

### Responsive layouts

レイアウトは画面の向きや端末自体のサイズなどを考慮したデザインとなっているべき。Composeではいろいろな画面構成に適用するための仕組みが用意されている。

#### Weight modifier in Row and Column

Composableのサイズはデフォルトでは自身がラップするコンテンツによってサイズが決定する。
Composableのサイズを自身の親によって柔軟にサイズが変わるようにするためには`weight`を利用する。

### Constraints

`BoxWithConstraints`を使うことで親からの制約を知り、それに応じたレイアウトを設計することができる。
`BoxWithConstraints`はラムダで指定したコンテンツ内でサイズを知ることができ、画面構成によってレイアウトを変更することができる。

#### Slot-based layouts

Composeは`androidx.compose.material:material`によってMaterial Designをベースとした`Drawer`, `FloatingActionButton`, `TopAppBar` などのComposableを提供し、UIの構築を容易にしている。

Material ComponentsはComposableの上にカスタマイズのレイヤーを追加するslot APIsを多用している。
このアプローチによってコンポーネントは子要素のすべての設定パラメータを公開するのではなく、自分自身を設定できる子要素を受け入れることでより柔軟になっている。Slotは開発者が必要に応じて埋められるように空のスペースを残している。
例えば、`TopAppBar`の場合は、ナビゲーションアイコン、タイトル、メニュー

Composableは通常`content`を受け取るが、Slot APIsでは複数の`content`が指定できるようになっている。
例えば、`TopAppBar`の場合は、`title`, `navigationIcon`, `actions`

`Scaffold`は基本的なMaterial Designのレイアウトの構成を実装することができ、`TopAppBar`, `BottomAppBar`, `FloatingActionButton`, `Drawer` などのスロットを提供しているので、簡単にこれらのコンポーネントを正しい位置に配置することができる。

### ConstraintLayout

シンプルなレイアウトでは`Column`や`Row`の利用が推奨されるがより複雑な配置が必要な場合は`ConstraintLayout`が便利。

Note: View systemでは、`ConstraintLayout`はネストされたレイアウトによるパフォーマンスの低下の観点で推奨されていたが、Composeでは階層が深いレイアウトでも効率的にハンドリングするので問題にならない。

Composeでの`ConstraintLayout`はDSLで動作する

* `createRefs()`, `createRefFor()` で参照を生成し、Composableごとに参照を保持する必要がある
* `constrainAs()` Modifierのラムダに制約を記述する
* `linkTo()`や他のメソッドを利用し制約を指定する
* `ConstraintLayout`自体の参照は`parent`を利用する

#### Decoupled API

画面の向きをもとに制約を変えたり、2つの制約間でアニメーションさせたいなど制約を分離したいケースでは以下の方法で実現できる。

1. `ConstraintLayout`のパラメータに`ConstraintSet`を渡す
1. `layoutId` Modifierを利用し、`ConstraintSet`内で参照をアサインする

### Custom layouts

ComposeではUI要素は呼び出された時にUIの一部を出力するComposable関数として表現され、それがレンダリングされるとUIツリーに追加される。各UI要素は1つの親と複数の子を持つ可能性があり、親の中での位置とサイズで指定される。

親は子の要素の制約を定義し、自身のサイズを定義するためにこれらの制約を尋ねる。制約とは要素の幅と高さの最小、最大を制限するものである。要素が子の要素を持つ場合、サイズを決定するために子の要素を測定することができる。

Note: Compose UIはmulti-pass計測ができず、異なる構成を試すために複数回計測するとはできない。

single-pass計測はパフォーマンスがよく、深いUIツリーでも効率的に扱えるようにしている。計測後に追加の情報がどうしても必要になるようなケースを効率的に扱うアプローチについては[intrinsic measurements](https://developer.android.com/jetpack/compose/layout#intrinsic-measurements)を参照。

#### Using the layout modifier

`layout` Modifierを使う事で要素をどのように計測、レイアウトするのかを変更することができる。

`paddingFromBaseline`を`layout`を使って実装するには

1. `measurable`パラメータを使い、`measurable.measure(constraints)`を呼び出すことで`Text`を計測する
1. `layout(width, height)`を呼び出すことでComposableのレイアウトを指定し、ラムダに要素を位置を指定することができる
1. `placealbe.place(x,y)`を呼び出すことで画面上のラップされた要素を位置付ける

#### Creating custom layouts

`layout` Modifierは呼び出したComposableのみ変更する。複数のComposableを計測、レイアウトするには`Layout` Composableを代わりに利用する。`Column`や`Row`は`Layout`を利用して構築されている。

Note: View systemではカスタムレイアウトでは`ViewGroup`の継承が必要だったが、Composableでは`Layout`を利用するだけ。

### Layout direction

Composableのレイアウト方向を変更するには`LocalLayoutDirection`を変更する。

画面上に手動でComposableを配置している場合、`LayoutDirection`は`layout` Modifierや`Layout` Composableの`LayoutScope`の一部となっている。

`layoutDirection`の利用時は`place`を利用しComposableを位置付ける。`place`ではLTR, RTLでの変更はされない。

### Intrinsic measurements

Composeでは子供の計測は1度のみというルールがあり2度計測すると例外がスローされる。しかし、計測前に子供の情報が必要となる場合がある。

Intrinsicsは実際の計測前に子供を照会することができる。

Composableに`intrinsicWidth`, `intrinsicsHeight`を尋ねることができる。

* `(min|max)IntrinsicWidth`：与えられた高さでコンテンツが描画できる最小幅、最大幅
* `(min|max)IntrinsicHeight`：与えられた幅でコンテンツが描画できる最小高さ、最大高さ

例えば、無限の幅の`Text`の`minIntrinsicHeight`は1行を描画できる高さを返す。

#### Intrinsics in action

2つの`Text`を横に並べて`Divider`で区切るレイアウトを`Row`を使って表現すると、`Divider`の高さは親の高さとなってしまう。

`Divider`の高さを`Text`の高さに合わせる場合は、`Row`に`height(IntrinsicSize.Min)`Modifierを指定する。
`Divider`に制約を与えないと`minIntrinsicHeight`は0になり、`Text`の`minIntrinsicHeight`が利用されるので、`Divider`の高さは`Row`の高さの制約に合わせられる。

### Learn more

[Layouts in Jetpack Compose codelab](https://developer.android.com/codelabs/jetpack-compose-layouts)
