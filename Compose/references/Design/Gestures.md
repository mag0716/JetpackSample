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

## Scrolling
