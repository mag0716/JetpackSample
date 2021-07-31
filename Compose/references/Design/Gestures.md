# Gestures

https://developer.android.com/jetpack/compose/gestures

Composeはユーザ操作を検出するためのさまざまなAPIが提供されている。
これらのAPIは幅広いユースケースをカバーしている。

* 最も共通的なユーザ操作はハイレベルAPIとして設計されている。
例：`clickable`はクリックを検知し、アクセシビリティ機能とタップ時の視覚効果を提供している
* 共通的でないケースではより柔軟にするために低レベルAPIとして提供されている。
例：`PointerInputScope.detectTapGestures`, `PointerInputScope.detectDragGestures`

## Tapping and pressing

`clickable` modifierはアプリに要素のクリックを検知できるようにする。

より柔軟に検知する必要がある場合は、`pinterInput` modifierを利用する。

Note: リストを表示したい場合、これらのAPIの代わりに`LazyColumn`, `LazyRow`に検討する。
`LazyColumn`, `LazyRow`は機能としてスクロールを保持し、必要な項目だけ表示するので効果的にスクロールが可能となっている。

### Scroll modifiers

`verticalScroll`,`horizontalScroll` modifierは最大サイズを超えて要素を表示する際にスクロールを可能にする。
`verticalScroll`,`horizontalScroll` modifierではコンテンツの変化やオフセットの指定などは必要ない。

`ScrollState`によりスクロールの位置を変更したり、現在の状態を取得したりできる。
デフォルトのパラメータで`ScrollState`を生成するには`rememberScrollState()`を利用する。

### Scrollable modifier

`scrollable` modifierが`scroll` modifierと異なる点は、`scrollable`はスクロール操作は検出するがコンテンツをオフセットしない点。
正しく動作するためには`ScrollableController`が必要で、各スクロールのステップで呼び出される`consumeScrollDelta`をピクセル単位のデルタで提供する必要がある。

### Nested Scrolling

Composeはスクロールのネストをサポートしており、一般的な例としてはリストの中にリストがあるケース。
もっと複雑なケースは https://material.io/components/app-bars-top#behavior

#### Automatic nested scrolling

単純なスクロールのネストではアプリ側の対応は不要で、自動的に子がそれ以上スクロールできなくなると親要素に伝搬される。

#### nestedScroll modifier

複数の要素で調整が必要な場合は、`nestedScroll` modifierを使用して、ネストされたスクロールの海藻を定義することで柔軟性を高めることができる。

### Drag

`draggable` modifierは単一方向のドラッグ操作に対するハイレベルなAPIであり、ドラッグの距離をピクセル単位で返す。

`scrollable`に似ているが、操作の検出のみを行う点に注意する必要があり、要素を移動させる場合には`offset`の更新などが必要。

ドラッグ操作全体を制御する必要がある場合は、`pointerInput` modifierを通じて実現することを検討する。

### Swipe

`swipeable` modifierを使用すると、要素を離したときに2つ以上のアンカーポイントに向かって移動するアニメーションが表示されるような方法でドラッグできる。
スワイプして閉じるを実現するためによく利用される。

このmodifierは要素を移動せず、操作の検出のみを行う点に注意する必要があり、要素を移動させる場合には`offset`の更新などが必要。

スワイプ可能な状態は`swipeable`modifierで必須であり、`rememberSwipeableState()`で作成し記憶することができる。
この状態ではアンカーへの移動のアニメーションを表示する便利なメソッドとドラッグの進行状況を観測するためのプロパティが利用できる。

スワイプ操作は`FixedThreshold(Dp)`や`FractionalThreshold(Float)`のような閾値を持つように構成できる。

境界を超えてスワイプする際には`resistance`を利用し、特定の位置に達していなくてもアニメーションするためには`velocityThreshold`も利用することで柔軟性を高めることができる。

### Multitouch: Panning, zooming, rotating

拡大、縮小、回転に利用されるマルチタッチ操作を検出するには`transformable` modifierを利用する。
このmodifierは操作の検出のみを行う。

他の操作と組み合わせる必要がある場合は、`PointerInputScope.detectTransformGestures`が利用できる。
