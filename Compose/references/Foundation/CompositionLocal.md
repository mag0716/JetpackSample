# Locally scoped data with CompositionLocal

https://developer.android.com/jetpack/compose/compositionlocal

`CompositionLocal`はCompositionを通じてデータを暗黙的に渡すためのツール。このページでは`CompositionLocal`とは何か、独自の`CompositionLocal`をどうやって作成するのか、`CompositionLocal`を使うべきユースケースを学習する。

## Introducing CompositionLocal

Composeでは通常、パラメータを通じて各Composableにデータを渡していく。これはComposableの依存関係が明示されるが色やタイプスタイルのように頻繁に利用するデータの場合では面倒になる。

色のようなデータをパラメータの指定なしでほとんどのComposableに渡すために、Composeでは`CompositionLocal`という機能があり、ツリーにスコープされた名前付きのオブジェクトを作成することでUIツリーを介してデータを渡すことができる。

`CompositionLocal`要素は通常UIツリーのノード内に値として提供する。その値はComposable関数のパラメータとして宣言しなくても利用することができるようになる。

Key Terms: このガイドでは `Composition`, `UI tree`, `UI hierarchy`という用語を利用しているが、他のガイドでの意味とは異なる。

`Composition`: Composable関数のコールグラフを記録したもの
`UI tree`, `UI hierarchy`: `LayoutNode`のツリーでCompositionのプロセスによって作成、更新される

`CompositionLocal`はMaterialテーマの内部で利用されている。`MaterialTheme`は色、タイポグラフィ、shapeの`CompositionLocal`を提供する。`LocalColors`, `LocalShapes`, `LocalTypography`プロパティとして用意され、`MaterialTheme`を通じて、`colors`, `shapes`, `typography`でアクセスすることができる。

`CompositionLocal`はCompositionの一部をスコープとしたインスタンスなのでツリーの異なるレベルに異なる値を提供することができる。`CompositionLocal`の`current`はそのCompositionの祖先が提供する最も近い値に対応する。

`CompositionLocal`に新しい値を提供するためには、`CompositionLocalProvider`と`provides`を利用する。`CompositionLocalProvider`の`content`ラムダは`CompositionLocal`の`current`プロパティにアクセスするときに呼び出され値を提供する。新しい値を提供すると、Composeは`CompositionLocal`を読み込んでいるCompositionの一部をRecompositionする。

`CompositionLocal`インスタンスはMaterial Composableによって内部で利用されており、`CompositionLocal`の現在の値にアクセスするために`current`プロパティを利用している。`LocalContext`の`current`を使ってリソースファイルを読み取ることもできる。

Note: `CompositionLocal`オブジェクトや定数は一般的に`Local`プレフィックスがついているのでIDE上での発見が容易になっている。

## Creating your own CompositionLocal

`CompositionLocal`は暗黙的にCompositionを通じてデータを受けわたすためのツールである。

`CompositionLocal`を使用するもう一つの重要なシグナルはパラメータが横断的であり、中間の実装層がその存在を意識するべきではない場合。例えば、Androidのパーミッションの問い合わせには`CompositionLocal`を利用する。メディアピッカーのComposableはデバイス上のパーミッションで保護されたコンテンツにアクセスするための新しい機能の追加をAPIを変更することなく、またメディアピッカーを呼び出す側は環境から使用される追加されたコンテキストを認識させることなく可能になる。

しかし、`CompositionLocal`は常にベストな解決方法ではない。`CompositionLocal`の使いすぎは欠点が出てくるので避けるべきである。

`CompositionLocal`はComposableの動作の理解を困難にする。`CompositionLocal`は暗黙的な依存を作るので、Composableの呼び出し側は全ての`CompositionLocal`の値が満たされていることを確認する必要がある。

さらに、Compositionの一部をミュータブルにすることが可能なので、この依存によって source of truth を満たせなくなる可能性がある。加えて、`current`がどこで提供されるのかを確認する必要がでてくるので問題が発生した際のデバッグがより困難になる。IDEの Find usages のようなツールやLayout Inspectorはこの問題の調査に必要な情報を十分に提供する。

Note: `CompositionLocal`は基礎的なアーキテクチャで動作しておりJetpack Composeはこれを多用している

### Deciding whether to use CompositionLocal

`CompositionLocal`がよい解決法になるユースケースとなる明確な条件がある。

`CompositionLocal`はよいデフォルト値を持つべきである。もしデフォルト値がない場合、開発者に`CompositionLocal`の値が提供されていない状況に陥らないように保証する必要がある。デフォルト値を提供しないとテストを作成したり、プレビューする際に常に明示的に提供する必要があるのでイライラすることになる。

ツリーのスコープやサブ階層のスコープを考慮しないコンセプトとした`CompositionLocal`は避ける。`CompositionLocal`はいくつかの子孫ではなく、どの子孫でも使用できる可能性がある場合に意味を持つ。

もしこれらの要素に合致しない場合は、`CompositionLocal`を作成する前に https://developer.android.com/jetpack/compose/compositionlocal#alternatives をチェックする。

特定の画面のViewModelを保持する`CompositionLocal`を作成し、その画面内のComposableがViewModelを取得して何らかのロジックを実行するようにするのは、必要とする特定のUI以外もそのViewModelを知ることができてしまうのでバッドプラクティス。再利用性やテスト容易性が高まるのでComposableに必要な情報のみを渡すのがゴッドプラクティス。

### Creating a CompositionLocal

`CompositionLocal` を作成するAPIは2通り存在する。

* `compositionLocalOf`:`current`を読み込んでいるコンテンツをRecompositionのみ更新する値を変更する
* `staticCompositionLocalOf`: `compositionLocalOf`と違ってComposeによって追跡されない。値を変更すると`current`を読み込んでいるコンテンツだけでなく、`CompositionLocal`が提供されているラムダ全体が再構成される

`CompositionLocal`に提供される値が変更される可能性が非常に低い、または変更されることがない場合は`staticCompositionLocalOf`を使用した方がパフォーマンスの利点がある。

例えば、アプリのデザインシステムでは、UIコンポーネントの影を利用して持ち上げる手段を取る可能性がある。異なるelevationはUIツリーを通じて受けわたすべきなので、`CompositionLocal`を利用する。`CompositionLocal`の値はシステムテーマに基づいて導き出されるため`compositionLocalOf` APIを利用する。

### Providing values to a CompositionLocal

`CompositionLocalProvider`は与えられた階層の代わりに`CompositionLocal`に値をバインドする。`CompositionLocal`に新しい値を提供するために、`provides` infix関数を利用して、`CompositionLocal`のkeyと値を紐づける。

### Consuming the CompositionLocal

`CompositionLocal.current`は直近の`CompositionLocalProvider`によって提供された値が返却される。

## Alternatives to consider

`CompositionLocal`はいくつかのユースケースでは過剰な解決方法になる可能性がある。もし、https://developer.android.com/jetpack/compose/compositionlocal#deciding に合致しないのであれば、他の解決法がより適切である可能性がある。

### Pass explicit parameters

Composableの依存関係を明確にすることは良い習慣であり、必要なものだけをComposableに渡すことが推奨される。Composableの再利用性を高めるために、Composableごとに可能な限り最小の情報を保持するべきである。

### Inversion of control

不必要な依存関係をComposableに渡さないようにするためのもう一つの方法は制御の反転である。子孫が依存関係を取り込んでロジックを実行する代わりに親がそれを代わりに行うようにする。

依存関係を子孫に渡さず、親がロジックの実行に責任を持つという制御逆転の原則を用いた代替案を考慮する。

このアプローチは子を親から切り離すためいくつかのユースケースに適している。親のComposableは複雑になりがちだが、下位レベルのComposableの柔軟性を高めることができる。

ロジックだけでなく、`@Composable`を渡す方法は同様の利点がある。
