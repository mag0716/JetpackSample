# Text in Compose

https://developer.android.com/jetpack/compose/text

Composeではテキストの表示とユーザ入力を操作する`BasicText`と`BasicTextFiled`が提供されており、Material Designガイドラインに従った`Text`と`TextField`も提供されている。

## Displaying text

テキストの表示には`Text`に`String`の引数を渡す方法が基本。

### Display text from resource

`Text`の引数にハードコーディングするのではなくAndroid Viewsと同様に文字列リソースを利用することが推奨されている。

### Styling text

`Text`はコンテンツのスタイルのために複数のパラメータを備えている。

これらのパラメータを指定するとテキスト全体に適用される。一部の文字だけに適用したい場合はmultiple inline stylesを参照。

#### Changing the text color

`color`

#### Changing the text size

`fontSize`

#### Making the text italic

`fontStyle`

#### Making the text bold

`fontWeight`

#### Text alignments

`textAlign`は`Text`の文字列の並びを設定することができる。

デフォルトではLTRは左側にRTLは右側に配置される。

もし手動で文字列の並びをセットする場合でも`TextAlign.Left`, `TextAlign.Right`よりも`TextAlign.Start`, `TextAlign.End`の利用が推奨。

Note: テキストの並びはレイアウトの並びとは異なり、詳細は[layouts documentation](https://developer.android.com/jetpack/compose/layout)を参照。

### Working with fonts

`fontFamily`

`res/fonts`に定義したカスタムフォントを指定することもできる。

typographyの設定方法については、[themes documentation](https://developer.android.com/jetpack/compose/themes#typography)を参照。

### Multiple styles in a text

同じ`Text`で異なるスタイルを指定したい場合は、`AnnotatedString`を利用する必要がある。

`AnnotatedString`は以下を含むdata class

* `Text`
* `SpanStyleRange`の`List`(テキスト内の一範囲を使用したインラインスタイル設定と同等)
* ` ParagraphStyleRange`の`List`(テキストの配置、方向、行の高さ、インデントスタイルを指定)

`TextStyle`は`Text`で利用され、`SpanStyle`と`ParagraphStyle`は`AnnotatedString`内で利用される。

`SpanStyle`と`ParagraphStyle`の違いは`ParagraphStyle`は段落全体に適用され、`SpanStyle`は文字に適用される。

`AnnotatedString`は type-safe builderとなっており生成が容易。

### Maximum number of lines

`Text`が表示する行数は`maxLines`で設定できる。

### Text overflow

長い文字列で表示しきれなくなった場合にどう表示するかについては`textOverflow`を設定する。

### Theming

Themeについては[themes documentation](https://developer.android.com/jetpack/compose/themes)を参照

### User interactions

`Text`のユーザー操作はmodifierの追加では対応できない。

#### Selecting text

デフォルトでは選択できずにテキストのコピーなどができない。有効にするためには、`SelectionContainer`でラップしてあげる必要がある。

`SelectionContainer`内のテキストの一部のみを選択不可にするには`DisableSelection`を利用する。

#### Getting the position of a click on text

`Text`のクリックは`clickable`を追加で対応できるが、クリックした位置を取得する必要がある場合は`ClickableText`を利用する必要がある。

#### Click with annotation

`Text`のクリック時にURLなど特定の情報が必要になった場合は、`AnnotatedString`を利用して、`tag`, `annotation`を指定する。

### Entering and modifying text

`TextField`はテキストの入力、変更が可能で2つのレベルの実装がある。

1. `TextField`はMaterial Designの実装でガイドラインに従って選択することを推奨する
  * デフォルトのスタイルは `filled`
  * `OutlinedTextField` は `outline`
1. `BasicTextField`はキーボードを通じてテキストの変更は許可しているが、ヒントやプレースホルダーなどの装飾は提供していない

#### Styling TextField

`TextField`, `BasicTextField`ともカスタマイズのために共通のパラメータを共有している。

* `singleLine`
* `maxLines`
* `textStyle`

Material Designを利用するのであれば`TextField`の利用が推奨される。Material Designを必要としない場合は`BasicTextField`を利用する必要がある。

#### Keyboard options

`TextField`はキーボードの設定オプションについて設定することが可能だが、いくつかのオプションについてはソフトウェアキーボードが準拠していない場合は利用できないかもしれない。サポートしているオプションは以下。

* `capitalization`
* `autoCorrect`
* `keyboardType`
* `imeAction`

#### Formatting

`TextField`は入力された値をパスワード入力のために`*`に置き換えたり、クレジットカード番号のように4文字ずつ`-`で区切るなどのフォーマットをさせることができる。

`visualTransformation`

サンプルは https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui-text/samples/src/main/java/androidx/compose/ui/text/samples/VisualTransformationSamples.kt
