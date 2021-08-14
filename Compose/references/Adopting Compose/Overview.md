# Adopting Compose in your app

https://developer.android.com/jetpack/compose/interop

Jetpack ComposeはViewベースなUIと共存できるように設計されている。
新しいアプリを作る場合は全てComposeで実装することがベストな選択肢かもしれないが、既存のアプリを修正する場合は一度に移行するのはできないかもしれない。
そういったケースでは、既存のUIとComposeを組み合わせて実現することが可能。

ViewベースなUIとComposeを統合するには主に2つの手法がある。

* Composeの要素を既存のUIに追加するには新しいComposeベースの画面を作成するか既存のActivity,Fragment,レイアウトにComposeを追加する
* ViewベースのUI要素をComposableに追加することで、`AndroidView`をComposeベースのデザインに追加できる

Note: ComposeとComposeでないコード間でStateを共有する必要がある場合は、`ViewModel`などで別クラスとして共有することが推奨される。
これらのクラスはunidirectional data flowに従っているべきである。
詳細は https://developer.android.com/jetpack/compose/interop/compose-in-existing-arch を参照。

アプリ全体をComposeへの移行を最も効果的に行うにはプロジェクトに必要な粒度で段階的に行う必要がある。
一度の以降では1画面ずつでも、Fragmentなどの再利用可能なUI要素などでも可能で次のようなアプローチをとることができる。

* ボトムアップアプローチ：ボタンなどのコンポーネントを移行し、次に`ViewGroup`を移行し、全てをComposableにしていく
* トップダウンアプローチ：`Fragment`や`ViewGroup`を移行してから、コンポーネントを移行していく

上記のアプローチは各画面が独立していることを前提としているが、デザインシステムなどの共通のUIを移行することも可能。
詳細は https://developer.android.com/jetpack/compose/interop/compose-in-existing-ui を参照。

以下のページで既存アプリとの統合について説明している。

https://developer.android.com/jetpack/compose/interop/interop-apis：ViewベースとComposeを組み合わせる時に手助けになるAPIについて
https://developer.android.com/jetpack/compose/interop/compose-in-existing-arch：ViewベースとComposeを組み合わせる方法
https://developer.android.com/jetpack/compose/interop/compose-in-existing-ui：ViewベースとCompose間で共有する方法
