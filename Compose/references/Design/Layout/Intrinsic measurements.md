# Intrinsic measurements in Compose layouts

https://developer.android.com/jetpack/compose/layouts/intrinsic-measurements

Composeでは子供の計測は1度のみというルールがあり2度計測すると例外がスローされる。しかし、計測前に子供の情報が必要となる場合がある。

Intrinsicsは実際の計測前に子供を照会することができる。

Composableに`intrinsicWidth`, `intrinsicsHeight`を尋ねることができる。

* `(min|max)IntrinsicWidth`：与えられた高さでコンテンツが描画できる最小幅、最大幅
* `(min|max)IntrinsicHeight`：与えられた幅でコンテンツが描画できる最小高さ、最大高さ

例えば、無限の幅の`Text`の`minIntrinsicHeight`は1行を描画できる高さを返す。

Note: 固有の測定値を求めることで子供を2回計測することはない。子供は測定される前に本質的な測定値がも尋ねられ、その情報に基づいて親は子供を測定するための制約を計算する。

## Intrinsics in action

2つの`Text`を横に並べて`Divider`で区切るレイアウトを`Row`を使って表現すると、`Divider`の高さは親の高さとなってしまう。

`Divider`の高さを`Text`の高さに合わせる場合は、`Row`に`height(IntrinsicSize.Min)`Modifierを指定する。
`Divider`に制約を与えないと`minIntrinsicHeight`は0になり、`Text`の`minIntrinsicHeight`が利用されるので、`Divider`の高さは`Row`の高さの制約に合わせられる。

### Intrinsics in your custom layouts

カスタム`Layout`や`LayoutModifier`を作成するとき、固有の測定値は近似値に基づいて自動的に計算される。そのため、全てのレイアウトで計算が正しく行われない場合がある。これらのAPIにはデフォルトを上書きするオプションが提供されている。

カスタム`Layout`の本質的な測定を指定するには作成時に`MeasurePolicy`の`minIntrinsicWidth`, `minIntrinsicHeight`, `maxIntrinsicWidth`, `maxIntrinsicHeight`を上書きする。

カスタム`LayoutModifier`を作成するときは、`LayoutModifier`の関連のメソッドを上書きする。

## Learn more

https://developer.android.com/codelabs/jetpack-compose-layouts#10
