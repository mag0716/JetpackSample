# Custom layouts

https://developer.android.com/jetpack/compose/layouts/custom

ComposeではUI要素は呼び出された時にUIの一部を出力するComposable関数として表現され、それがレンダリングされるとUIツリーに追加される。各UI要素は1つの親と複数の子を持つ可能性があり、親の中での位置とサイズで指定される。各UI要素は`(x,y)`で指定された位置と`width`, `height`で指定されたサイズにて配置される。

親は子の要素の制約を定義し、自身のサイズを定義するためにこれらの制約を尋ねる。制約とは要素の幅と高さの最小、最大を制限するものである。要素が子の要素を持つ場合、サイズを決定するために子の要素を測定することができる。要素が地震のサイズを決定して通知すると、要素は自身の子要素の配置方法を定義する機会がある。

Note: Compose UIはmulti-pass計測ができず、異なる構成を試すために複数回計測するとはできない。

single-pass計測はパフォーマンスがよく、深いUIツリーでも効率的に扱えるようにしている。計測後に追加の情報がどうしても必要になるようなケースを効率的に扱うアプローチについては[intrinsic measurements](https://developer.android.com/jetpack/compose/layout#intrinsic-measurements)を参照。

スコープの使用により、いつ測定や子の配置ができるかを定義する。レイアウトの測定は測定パスとレイアウトパスの間のみに行うことができ、子供の配置はレイアウトパスの間にのみ行うことができ、事前に測定された後にのみ行うことができる。これらは`MeasureScope`や`PlacementScope`などのスコープによりコンパイル時に矯正される。

## Using the layout modifier

`layout` Modifierを使う事で要素をどのように計測、レイアウトするのかを変更することができる。

`paddingFromBaseline`を`layout`を使って実装するには

1. `measurable`パラメータを使い、`measurable.measure(constraints)`を呼び出すことで`Text`を計測する
1. `layout(width, height)`を呼び出すことでComposableのレイアウトを指定し、ラムダに要素を位置を指定することができる
1. `placealbe.place(x,y)`を呼び出すことで画面上のラップされた要素を位置付ける

## Creating custom layouts

`layout` Modifierは呼び出したComposableのみ変更する。複数のComposableを計測、レイアウトするには`Layout` Composableを代わりに利用する。`Column`や`Row`は`Layout`を利用して構築されている。

Note: View systemではカスタムレイアウトでは`ViewGroup`の継承が必要だったが、Composableでは`Layout`を利用するだけ。

## Layout direction

Composableのレイアウト方向を変更するには`LocalLayoutDirection`を変更する。

画面上に手動でComposableを配置している場合、`LayoutDirection`は`layout` Modifierや`Layout` Composableの`LayoutScope`の一部となっている。

`layoutDirection`の利用時は`place`を利用しComposableを位置付ける。`place`ではLTR, RTLでの変更はされない。

## Custom layouts in action

Codelab は https://developer.android.com/codelabs/jetpack-compose-layouts#0
カスタムレイアウトのサンプルは https://github.com/android/compose-samples/search?q=androidx.compose.ui.layout.Layout を参照。
