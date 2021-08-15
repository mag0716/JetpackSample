# ConstraintLayout in Compose

https://developer.android.com/jetpack/compose/layouts/constraintlayout

シンプルなレイアウトでは`Column`や`Row`の利用が推奨されるがより複雑な配置が必要な場合は`ConstraintLayout`が便利。

Warning: `constraintLayout-compose`はJetpack Composeのバージョニングとは異なる。最新は https://developer.android.com/jetpack/androidx/releases/constraintlayout にて確認する。

Note: View systemでは、`ConstraintLayout`はネストされたレイアウトによるパフォーマンスの低下の観点で推奨されていたが、Composeでは階層が深いレイアウトでも効率的にハンドリングするので問題にならない。

Note: 特定のUIで`ConstraintLayout`を使用するかは開発者の好みによる。View systemではパフォーマンスの高いレイアウトを構築する方法として利用されていたが、Composeではこの点は問題にならないので、可読性と保守性に役立つかを考慮すること。

Composeでの`ConstraintLayout`はDSLで動作する

* `createRefs()`, `createRefFor()` で参照を生成し、Composableごとに参照を保持する必要がある
* `constrainAs()` Modifierのラムダに制約を記述する
* `linkTo()`や他のメソッドを利用し制約を指定する
* `ConstraintLayout`自体の参照は`parent`を利用する

## Decoupled API

画面の向きをもとに制約を変えたり、2つの制約間でアニメーションさせたいなど制約を分離したいケースでは以下の方法で実現できる。

1. `ConstraintLayout`のパラメータに`ConstraintSet`を渡す
1. `layoutId` Modifierを利用し、`ConstraintSet`内で参照をアサインする

## Learn more

Codelabは https://developer.android.com/codelabs/jetpack-compose-layouts#9
サンプルは https://github.com/android/compose-samples/search?q=ConstraintLayout
