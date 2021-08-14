# Resources in Compose

https://developer.android.com/jetpack/compose/resources

Jetpack Composeはプロジェクト内にあるリソースにアクセスすることができ、このドキュメントではアクセスするためのいくつかのAPIについて説明している。

リソースはBitmap、レイアウト定義、文字列、アニメーションなどの追加ファイルや静的なコンテンツでコードで利用することができる。
リソースの詳細は https://developer.android.com/guide/topics/resources/providing-resources を参照。

## Strings

XMLファイルに定義された文字列を取得するには `stringResource` APIを利用する。

`stringResource`はフォーマットも可能。

### String plurals

Composeは複数形のStringを直接取得するメソッドを提供していないが、`getQuantiyString`を利用することができる。
現在の`Context`から`Resource`にアクセスするためには`LocalContext`を利用する。
Composition locals については、https://developer.android.com/jetpack/compose/interop を参照。

## Dimensions

XMLファイルに定義されたdimenを取得するには `dimensionResource` APIを利用する。

## Colors

XMLファイルに定義された色を取得するには`colorResource` APIを利用する。

`colorResource`は静的な色については期待通り動作するが、color state listの場合はフラット化される。

Warning: 色をハードコーディングするよりもテーマを利用することが推奨されている。
`colorResource`で取得することは可能だが、`MaterialTheme`を定義し、`MaterialTheme.colors.primary`などにアクセスすることが推奨される。
詳細は https://developer.android.com/jetpack/compose/themes を参照。

## Vector assets and image resources

vector drawablesとPNGなどのラスタライズされたアセットは`painterResource` APIを利用することでアクセスすることができる。
`painterResource`を利用すれば、drawableの種類が何かを知っておく必要はない。

`painterResource`はメインスレッド上でデコードとパースを行う。

Note: `Context.getDrawable`と違って、`painterResource`は`BitmapDrawable`, `VectorDrawable`　のみ読み込むことができる。

## Animated Vector Drawables

animated vector drawableは`animatedVectorResource` APIを利用して読み込むことができ、`AnimatedImageVector`を返却する。
animated imageを表示するためには、`painterFor`で`Painter`を生成し、`Image`, `Icon`で利用する必要がある。
`atEnd`
パラメータはアニメーションが終わっても描画しておくかどうかを指定できる。
もし、mutable stateを利用し、この値が変化するとアニメーションが実行される。

## Icons

Jetpack ComposeにはMaterial Iconsを利用するためのエントリーポイントである`Icons`が提供されている。
Filled, Outlined, Rounded, TwoTone, Sharp の5つのテーマがあり、テーマごとに同じアイコンが含まれている。
一般的には1つのアプリでは統一性を持たせるために1つのテーマを選択すべき。

アイコンを描画するためには、`Icon` Composableを利用し、tintとサイズを提供する。

いくつかの最も一般的なアイコンについては`androidx.compose.material`に含まれているが、それ伊賀アイコンについては `material-icons-extended` への依存を追加する必要がある。

Note: アイコンはMaterialによって定義された名前で維持されているが、snake_caseはPascalCase
に変換されている。
また、数字始まり場合は `_360` などのように_始まりとなっている。

Warning: `materialcions-extended` はサイズが大きくアプリサイズに影響を与える可能性がある。
そのためプロダクト版はR8/Proguardを利用し、使用していないリソースを取り除くことを検討する必要がある。
また、サイズが大きいため開発中はビルド時間とAndroid Studioのプレビューの読み込み時間が増加する可能性がある。
その一方で、全てのアイコンの最新バージョンにアクセスすることができ、アイコンのソースコードをコピーしてプロジェクト内に持つこともできる。
Android StudioのImport vector asset機能でも同等のことが実現できる。

## Fonts

Composeでフォントを利用するには、`res/font`にフォントを配置しておく必要がある。

Note: Downloadable FontsはComposeでは未サポート

`Font` APIを利用して各フォントファイルを読み込み、それらを利用して`FontFamily`を作成する。
これを`TextStyle`インスタンスで使用することで、独自の`Typography`を作成できる。

Typographyの詳細は https://developer.android.com/jetpack/compose/themes#typography を参照。
