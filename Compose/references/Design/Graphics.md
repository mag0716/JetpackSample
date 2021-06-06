# Graphics in Compose

https://developer.android.com/jetpack/compose/graphics

## Declarative graphics with Compose

Composeではグラフィックを扱う方法に宣言的アプローチはいくつかの利点がある。

* グラフック要素の状態を最小化することで状態プログラミングになることを避けている
* 描画時にすべてのオプションがComposable関数で想定通りに正しく機能する
* ComposeのグラフィックAPIが効率的な方法でオブジェクトを作成、解放する

Note: Composeは内部でView-baseのUIの`Canvas`などに依存しているが`Paint`オブジェクトの扱いなどComposeが代わりに行ってくれることで簡素化されている。

## Canvas

カスタムグラフックのための主なComposableが`Canvas`で他のCompose UI要素と一緒にレイアウト上に配置することができる。`Canvas`内ではスタイルや位置を正確にコントロールしながら要素を描画することができる。

Note: `Canvas` ComposableはView-baseの`Canvas`を生成、管理し、状態の維持やヘルパーオブジェクトの作成、解放などを代行する。

`Canvas`は自動的に自身の状態を維持する描画にまつわるスコープである`DrawScope`を公開する。`DrawScope`は`size`などの便利なフィールドを提供する。

* `drawLine`
  * 線を描画
  * デフォルトでは1px
  * `strokeWidth`
    * 線の太さ
* `drawRect`
* `drawCircle`

## DrawScope
