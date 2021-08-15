# Alignment lines in Jetpack Compose

https://developer.android.com/jetpack/compose/layouts/alignment-lines

Composeのレイアウトモデルは`AlignmentLine`を利用して親のレイアウトが子レイアウトの配置に利用できるカスタムの配置線を作成することができる。例えば、`Row`はカスタムの配置線を利用して子の位置を揃えることができる。

特定の`AlignmentLiine`のためにレイアウトが値を提供すると、`Placeable`インスタンス上の`Placeable.get`operatorを利用してレイアウトの親は計測の後に値を読み込むことができる。`AlignmentLine`の位置に基づいて、親は子供の位置を決定することができる。

ComposeのいくつかのComposableはすでに配置線を利用している。例えば、`BasicText`は`FirstBaseline`と`LastBaseline`を後悔している。

`LayoutModifier`はテキストの初めのベースラインからパディングを追加するために`FirstBaseline`を読み込み`firstBaselineToTop`を呼び出している。

サンプルで利用されている`placeable[FirstBaseline]`で`FirstBaseline`を読み込んでいる。

Note: `firstBaselineToTop`Modifierは学習目的のサンプルで、Composeのライブラリは指定したパディングを定義するために`paddingFrom`が用意されている。

## Creating custom alignment lines

カスタム`Layout`やカスタム`LayoutModifier`を生成するとき、カスタム配置線を提供することができ、他の親Composableがそれを利用して子を配置することができる。

Note: カスタム`Layout`や`LayoutModifier`について詳細を知りたい場合は https://developer.android.com/jetpack/compose/layouts/custom

棒グラフを表現する`BarChart`Composableでは`MaxChartValue`と`MinChartValue`が公開されており、最大値と最小値のデータを並べる。`Max`と`Min`のテキストはカスタム配置線の中心に配置する。

カスタム配置線はプロジェクトのトップレベル変数として定義される。

`HorizontalAlignmentLine`は子供を垂直に配置するために利用される。

複数のレイアウトがこれらの配置線の値を提供する場合にはパラメータとしてマージポリシーが渡される。`Canvas`は左上が(0,0)となるので`MaxChartValue`は`MinChartValue`よりも小さくなるようにマージポリシーが定義される。

カスタム`Layout`や`LayoutModifier`を作成するとき、`MeasureScope.layout`内で`alignmentLines: Map<AlignmentLine, Int>`パラメータを受け取りカスタム配置線を指定する。

Note: `BarChart`Composableのソースコードは https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/integration-tests/docs-snippets/src/main/java/androidx/compose/integration/docs/layout/CustomAlignmentLines.kt を参照

Composableの直接、間接的な親は配置線を消費することができる。棒グラフの例では2つのテキストとデータを受け取り、2つのテキストをデータの最大値と最小値に合わせて配置するカスタムレイアウトを作成する。
