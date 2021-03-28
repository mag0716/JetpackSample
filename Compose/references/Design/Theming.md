# Theming in Compose

https://developer.android.com/jetpack/compose/themes

## Application-wide theming

Jetpack ComposeはMaterial Designの実装を提供している。Material Designのコンポーネント(ボタン、カード、スイッチなど)はMaterial Themingの上に構築されている。テーマは色、タイポグラフィ、シェイプの各属性で構成されており、それらを変更するとコンポーネントに自動的に反映される。

これらのコンセンプトをJetpack Composeでは `MaterialTheme` で実現している

### Color

Dark Themeのように複数のテーマをサポートが可能となるため、テーマで色を指定し、そこから色を取得することが強く推奨されている。

ComposeはMaterial color systemを構築するために`Colors`が提供されており、`light`と`dark`の色のセットを作成することができる。

#### Using theme colors

`MaterialTheme.colors`を利用することによって`MaterialTheme`から`Colors`を取得することが可能。

#### Surface and content color

多くのコンポーネントは`color`と`contentColor`の受け取ることができ、Composableの色だけではなく、例えばテキストやアイコンのtintなどのComposable内のコンテンツやComposableの設定が可能となる。

`contentColorFor()`は`primary`を指定したら`onPrimary`の色が取得されるなど、`onXXX`の色が取得することができる。`LocalContentColor`を使うことで現在の背景と対象的な現在のコンテンツカラーを取得できる。

Note:　要素の背景色を設定する場合は、`Modifier.background()`だとコンテンツカラーが設定されないので、`Surface`の利用が推奨。

#### Content Alpha

Material Designでは、重要性を伝えたり、視覚的な階層性を持たせるために異なるレベルの不透明度を指定することが推奨されている。

Jetpack Composeでは`LocalContentAlha`を通じて実装でき、`CompositionLocal`を提供することによってコンテンツのalphaを指定することができる。子供のComposableはalpha値を利用することが可能で例えば`Text`や`Icon`などはデフォルトでは`LocalContentAlpha`を使って調整された`LocalContentColor`の組み合わせが使われる。Materialは`ContentAlpha`に標準で用意された`high`, `medium`, `disabled`を指定できる。

#### Dark theme

Composeでは`MaterialTheme`の`Colors`を指定することでlightとdarkテーマを実装することができる。

`MaterialTheem.colors.isLight`で現在の色をチェックすることができる。

Materialではdark themeのsurfaceで高いelevationだと背景色が明るくなるが、`Surface`を利用することで自動的に実装できる。

#### Extending Material colors

Color Themingの色から拡張させたい場合は、アプリ用に色を指定することもできる。

### Typography

Material design ではtype systemが定義されており、意味のある名前を持つ少数のスタイルを使うことが推奨されている。

Composeでは`Typography`, `TextStyle` とフォントに関係するクラスを利用することでtype systemを実装することができ、カスタマイズしたいものだけ`Typography`に指定することができる。

全体で同じフォントを利用したい場合は、`defaultFontFamily`を指定し、`TextStyle`の`fontFamily`を省略する。

Note: Composeでは組み込まれたフォントリソースはサポートしているが、Downloadable Fontsはまだサポートされていない。

### Using text styles

テーマから`TextStyle`を取得するためには、`MaterialTheme.typography`から取得する。

## Shape

Material designではshape systemが定義されており、large, medium, smallのコンポーネントに対するshapeの定義が可能。

Composeは各カテゴリに`CornerBasedShape`を指定させる`Shapes`を使ってshape systemを実装する。

多くのコンポーネントはこれらのshapeをデフォルトで利用されている。
small：`Button`, `TextField`, `FloatingActionButton`
medium：`AlertDialog`
large：`ModalDrawerLayout`

詳細は https://material.io/design/shape/applying-shape-to-ui.html#shape-scheme を参照。

### Using shape

テーマからshapeを取得するためには、`MaterialTheme.shapes`から取得する。

## Component styles

Componentにはコンポーネントスタイルの明確な概念はなく、独自のComposableを作成することで実現できる。例えば、ボタンのスタイルを作成するには、ボタンを独自のComposable関数でラップし、変更したいパラメータを直接設定することで実現する。

## Custom design system

Jetpack ComposeではMaterialの実装を推奨しているが限定されてはおらず、独自のデザインシステムの作成は可能。

独自のデザインシステムの構築方法は以下を参照。

* [`MaterialTheme`のソース](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material/material/src/commonMain/kotlin/androidx/compose/material/MaterialTheme.kt)
* [`CompositionLocal`](https://developer.android.com/reference/kotlin/androidx/compose/runtime/CompositionLocal)
* [`Providers`](https://developer.android.com/reference/kotlin/androidx/compose/runtime/package-summary#CompositionLocalProvider(androidx.compose.runtime.ProvidedValue,%20kotlin.Function0))
* [独自のデザインシステムを構築しているサンプル](https://github.com/android/compose-samples/tree/main/Jetsnack)

## Learn more
