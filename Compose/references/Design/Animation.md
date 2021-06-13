# Animation

https://developer.android.com/jetpack/compose/animation

## Overview

最新のモバイルアプリではスムーズで分かりやすいUXのためにアニメーションは欠かせない。
多くのJetpack ComposeのAnimation APIは他の要素と同様にcomposable関数となっており、suspend関数として構築されている。
このドキュメントでは実用的なシーンで役立つハイレベルなAPIから始まり、カスタマイズを可能にするローレベルなAPIについて説明している。

以下のチャートではアニメーションの実装にどのAPIを利用するのかの決定に役立つ。

* レイアウト内のコンテンツを変更したい
  * 開始/終了の遷移をアニメーションさせたい -> `AnimationVisibility`
  * コンテンツのサイズ変更をアニメーションさせたい -> `Modifier.animateContentSize`
  * その他 -> `Crossfade`
* stateをベースでcomposition中にアニメーションが発生する
  * 無限にアニメーションさせたい -> `rememberInfiniteTransition`
  * 複数の値を同時にアニメーションさせたい -> `updateTransition`
  * その他 -> `animate*AsState`
* アニメーション時間をコントロールしたい -> `Animation`
* アニメーションが唯一の情報源とする場合 -> `Animatable`
* その他 -> `AnimationState` or `animate`

## High-level animation APIs

Composeは多くのアプリで利用されるであろう共通のアニメーションのいくつかをAPIとして提供している。
これらのAPIはMaterial Design Motionのベストプラクティスに合わせて調整されている。

### AnimatedVisibility(experimental)

Caution: Experimental APIは将来的に変更、削除される可能性がある。

`AnimatedVisibility`は自身のコンテンツの表示、非表示をアニメーションさせる。

デフォルトでは表示はフェードインと拡大、非表示はフェードアウトと縮小。
`EnterTransition`, `ExitTransition` でカスタマイズが可能。

`ExternalTransition`, `ExitTransition` は `+` operatorで複数を組み合わせることができる。

### animateContentSize

`modifier`に`animateContentSize`を指定することでサイズ変更時にアニメーションする。

### Crossfade

`Crossfade`は二つのレイアウト間をクロスフェードアニメーションで切り替える。
`current` を変更することでコンテンツはアニメーションして切り替わる。

## Low-level Animation APIs

全てのハイレベルAPIはローレベルAPIによって構成されている。

`animate*AsState`はあたいの変化をアニメーションの値として表示する最もシンプルなAPI。
これはCoroutineベースなAPIである`Animatable`によって支えられている。
`updateTransition`は複数の値を扱うことができStateの変更によってアニメーションするオブジェクトを作成する。
`rememberInfiniteTransition`は似ているが、無限に実行するオブジェクトを作成する。
`Animatable`を除く全てのAPIはcomposition外で作成できることを意味している。

これらのAPIのベースとなっており、より基礎的なAPIが`Animation`。
大部分のアプリでは`Animation`を扱うことはないが、`Animation`のカスタマイズ機能の中にはハイレベルAPIを通じて利用できるものもある。
アニメーションのカスタマイズについては、https://developer.android.com/jetpack/compose/animation#customize-animations を参照。

### animate*AsState

`animate*AsState`は単一の値でアニメーションする最もシンプルなAPI。
アニメーションの開始と終了の値を渡すだけで定義することができる。

例：alpha値の変化をアニメーションさせる

アニメーションのインスタンスを作成や割り込みを処理したりする必要はない。
アニメションオブジェクトが呼び出し場所で作成、記憶される。
そこに到達すると、異なる値がcomposableに供給され、自動的に値に従ったアニメーションが実行される。
もしアニメーション中の場合は、現在の値から始まり最終の値までアニメーションが実行される。
アニメーション中は毎フレームごとにrecomposeされる。

`animate*AsState`では、`Float`, `Color`, `Dp`, `Size`, `Bounds`, `Offset`, `Rect`, `Int`, `IntOffset`, `IntSize`を扱うことができる。
他の型を扱いたいときは`TwoWayConverter`を利用してやりとりすることが可能。

`AnimationSpec`のカスタマイズについては https://developer.android.com/jetpack/compose/animation#animationspec を参照。

### Animatable

`Animatable`は`animateTo`を通じて値の変化でアニメーションさせることができる値を保持する。
このAPIは`animate*AsState`で利用されている。
これは一貫した継続性と相互排他性を保証し、値の変化は常に継続し進行中のアニメーションはキャンセルされることを意味する。

`Animatable`の多くの機能はsuspend関数として提供されている`animateTo`を含んでいる。
これは適切なcoroutine scope内でラップする必要があるということを意味している。
例えば、特定のキーバリュー間のscopeの作成には`LaunchedEffect`を利用することができる。

値の変化がアニメーション中に発生した場合、アニメーションはキャンセルされ、現在の値から新しいアニメーションが開始される。

このアニメーションの実装は`animate*AsState`で利用されている。
`animate*AsState`と比較すると、`Animatable`はより細かい制御が可能になる。
まず、`Animatable`はアニメーション開始時の値とは異なる初期値を持つことができる。
例えば、灰色から赤、緑色に変化するアニメーションの実装が可能。
次に、`Animatable`はコンテンツの値をより操作する`snapTo`, `animateDecay`が提供されている。
`snapTo`は指定した値を即座に変更し、アニメーション自体が唯一の情報源ではなくタッチイベントなどの他の状態と同期する必要がある場合に便利。
`animateDecay`は与えられた加速度で減速するアニメーションを開始し、フリングの動作を実装するのに便利。
詳細は https://developer.android.com/jetpack/compose/animation#gesture-and-animation を参照。

`Animatable`では`Float`, `Color`のみがサポートされているが、`ToWayConverter`を利用することで他の型を扱うことができる。
詳細は https://developer.android.com/jetpack/compose/animation#animationvector を参照。

アニメーションをカスタマイズのために `AnimationSpec` が提供されている。
詳細は https://developer.android.com/jetpack/compose/animation#animationspec を参照。

### updateTransition

`Transition`は1つ以上のアニメーションを管理し、複数の状態間を同時に実行する。

状態にはいずれの型が利用でき、多くの場合は `enum` が利用できる。

`updateTransition` は `Transition`の作成と保持を行い、自身の状態を更新する。

`animate*` 拡張関数を使い、子供のアニメーションを定義する。
状態ごとに値を指定し、`animate*` は `updateTransition` で更新された時、アニメーションのフレームごとに更新された値を返却する。

`transitionSpec` パラメータを指定することで、異なる`AnimationSpec`を指定することができる。
詳細は https://developer.android.com/jetpack/compose/animation#animationspec を参照。

アニメーションが目標の状態に到達すると、`Transition.currentState`は`Transition.targetState`と同じになる。
これはアニメーションが完了したかどうかに利用することができる。

`MutableTransitionState`と`updateTransition`を一緒に利用することで最初の目標の状態とは異なる初期状態を表現することができる。
例えば、compositionが開始されるとすぐにアニメーションが開始される。

### Encapsulate a Transition and make it reusable

シンプルなユースケースのではアニメーションは同じcomposableに定義するかどうかはオプション。
ただし、多数のアニメーション値を持つ複雑なコンポーネントの場合はUIから分離した方がよい。

それを実現するためには全てのアニメーション値を保持し`update`で自身のインスタンスを返すクラスを作成する。
遷移の実装は他の関数に抽出することができ、アニメーションロジックをまとめ、再利用するのに便利。

### Tooling support

Android StudioはCompose Preview内でアニメーションを検査することができる。

* フレームごとのアニメーションのプレビュー
* アニメーション中の値
* 初期、最終の状態間のアニメーションのプレビュー

アニメーションの検査を始めるとプレビューの下に`Animations`パネルが表示される。
アニメーションの値ごとにデフォルトの名前でラベリングされており、`updateTransition`, `animate*` の `label` パラメータをしていすることでカスタマイズできる。
詳細は https://developer.android.com/jetpack/compose/tooling#layout-preview を参照。

Note: この機能は手動で有効化する必要がある。

### rememberInfiniteTransition

`InfiniteTransition`は`Transition`のような子供のアニメーションを1つ以上保持しているが、アニメーションはcompostionに入るとすぐに開始され、削除されるまで停止されない。
`rememberInfiniteTransition`で`InfiniteTransition`のインスタンスを作成できる。
子供のアニメーションは`animateColor`, `animatedFloat`, `animatedValue` を使用して追加することができる。
`AnimationSpec`を指定するには`infiniteRepeatable`を指定する必要がある。

### TargetBasedAnimation

`TargetBasedAnimation`はここまで説明した中で最も低レベルなAPI。
他のAPIはほとんどのユースケースに対応はしているが、`TargetBasedAnimation`を使用することでアニメーションの再生時間を自分で制御することができる。
`willFrameMillis`に渡した値で手動でコントロールすることができる。

## Customize animations

### AnimationSpec

ほとんどのアニメーションAPIはアニメーションをカスタマイズするために`AnimationSpec`パラメータをもっている。

#### spring

`spring`は物理ベースのアニメーションを作成し、`dampingRatio`と`stiffness`パラメータを持つ。

`dampingRation`はバネがどのように跳ねるかを定義し、デフォルト値は`Spring.DampingRatioNoBouncy`

`stiffness`は終了までどの速さになるかを定義し、デフォルト値は`Spring.StiffnessMedium`

`spring`は加速度の継続性が保証されているため`AnimationSpec`よりもスムーズなアニメーションがかのう。
`spring`は`animate*AsState`, `updateTransition`のような多くのAPIの`AnimationSpec`のデフォルト値として利用されている。

#### tween

`tween`はイージングカーブを使用して、指定された`durationMillis`の開始と終了の間をアニメーションする。
詳細は https://developer.android.com/jetpack/compose/animation#easing を参照。
`delayMillis`を指定することでアニメーションの開始を遅延させることもできる。

#### keyframes

`keyframes`はアニメーション中の異なるタイムスタンプで指定された値に基づいてアニメーションする。
アニメーションの値は常に2つのキーフレーム間で補間され、イージングを指定して補間曲線を設定できる。

0msとアニメーション時間の指定はオプションとなっており、デフォルト値は開始と終了値が設定される。

#### repeatable

`repeatable`は指定された回数になるまで時間ベースのアニメーション(`tween`, `keyframes`など)を繰り返す。
`repeatMode`パラメータを渡すと、最初から開始するか、最後から開始するかを指定できる。

#### infiniteRepeatable

`infiniteRepeatable`は`repeatable`に似ているが繰り返し回数が無限である点がことなる。

`ComposeTestRule`を使用したテストでは`infiniteRepeatable`を使用したアニメーションは実行されず、初期値を使用してレンダリングされる。

#### snap

`snap`は値をすぐに終了値に切り替える特別な`AnimationSpec`。
`delayMillis`を指定してアニメーションの開始を遅延させることができる。

Note: View-baseでは時間ベースのアニメーションは`ObjectAnimator`など、物理ベースのアニメーションは`SpringAnimation`を使用する必要がある。
これらの2つの異なるアニメーションを同時に使用することが困難だったが、Composeでは`AnimationSpec`を使用することで統一された方法で処理できる。

### Easing

時間ベースのアニメーション(`tween`, `keyframes`)の`AnimationSpec`は`Easing`を使用してアニメーションの割合を調整する。
これによりアニメーションの値の速度を変えながら動かすことができる。
割合は0(開始)から1.0(終了)までの値でアニメーションの現在の位置を示す。

`Easing`とは0-1.0の小数値を受け取り、浮動小数点を返す関数。
戻り値は境界外となる場合もある。

Composeにはほとんどのユースケースに対応できる`Easing`関数が用意されている。
詳細は https://material.io/design/motion/speed.html#easing を参照。

* `FastOutSlowInEasing`
* `LinearOutSlowInEasing`
* `FastOutLinearEasing`
* `LinearEasing`
* `CubicBezierEasing`

Note: `Easing`は`Interporator`クラスと同じように動作する。
`getInterpolation()`の代わりに`transform()`を使用する。

### AnimationVector

ほとんどのアニメーションAPIは`Float`, `Color`, `Dp`など基本的な型をサポートしている。
ただし、カスタムされた型を利用する必要がある場合もある。
アニメーション中、値は`AnimationVector`として表され、値は`TwoWayConverter`によって変換される。
これによってコアアニメーションシステムが値を均一に処理できるようになっている。
`Int`は`AnimationVector1D`として表される。

`Color`はARGBの4つの値のセットであるため、`AnimationVector4D`に変換される。
このように次元数に応じて変換される。
これによりオブジェクトの異なるコンポーネントを別々にアニメーション化し、それぞれの速度をトラッキングすることができる。
基本的な型のコンバータには`Color.VectorConverter`, `Dp.VectorConverter`などを使用してアクセスできる。

新しい型をサポートする場合は`TwoWayConverter`を使用してAPIに指定することができる。

### Gesture and animation (advanced)

ユーザ操作を伴うアニメーションの場合は、ユーザ操作時に実行中のアニメーションを中断させる必要があるなど考慮すべき点がいくつかある。

タップイベントは`pointerInput` modifierで処理され、新しいタップイベントを検出するとブロック内の処理が実行される。

もう1つよくあるパターンはドラッグなどのイベントとアニメーションの値を同期させる場合。
例：スワイプで閉じる

ドラッグイベントでは`snapTo`を使用してイベントから計算された値で`Animatable`の値を更新する。
フリングについては`VelocityTracker`を利用すると速度を計算でき、速度は`animateDecay`に送られアニメーションに使用される。
オフセットを元に戻すには`animateTo`で`targetValue`に`0f`を渡す。

### Testing

`ComposeTestRule`を利用するとクロックを制御しながらアニメーションのテストを作成することができる。
これにより、アニメーション中の値を検証することができる。
また、テストでは実際のアニメーション時間よりも短い時間でアニメーションを実行できる。

`ComposeTestRule`はクロックを`mainClock`として公開し、`autoAdvance`に`false`を渡すことでクロックを制御可能にしたら、`advanceTimeBy`で制御することができる。

`advanceTimeBy`は指定した時間だけ正確に進めるのではなく、フレーム間隔の倍数に最も近い時間に切り上げられることに注意が必要。

### Learn more

Codelab は https://developer.android.com/codelabs/jetpack-compose-animation
