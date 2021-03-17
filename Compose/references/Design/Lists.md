# Lists

https://developer.android.com/jetpack/compose/lists

スクロールが必須でない場合は、`Column`, `Row` の利用が適しているかもしれない。
`Column`をスクロールさせる場合は、`verticalScroll()`を利用する。

## Lazy composables

もし数が多かったり、不明な項目を表示する場合は、`Column` を利用すると、見えているかどうかに関わらず全てレイアウトするのでパフォーマンスの問題が発生する可能性がある。

Composeは見えている項目のみをレイアウトするための`LazyColumn`や`LazyRow`などのコンポーネントが用意されている。

Note:`RecyclerView` と同じ原則に従っている。

遅延コンポーネントはComposeのほとんどのレイアウトは異なり、@COmposableコンテンツをブロックパラメータで受け取る代わりに、アプリが直接Composablesを発行できるように、`LazyListScope()`を提供している。
`LazyListScope`ブロックはコンテンツを記述するためのDLSを提供する。

Key Term：DSLとはdomain-specific language。詳細は https://developer.android.com/jetpack/compose/kotlin#dsl を参照

## LazyListScope DSL

`LazyListScope`のDSLはレイアウト内の項目を記述するための関数が多数用意されており、基本的な関数では1つの項目を追加する`item()`、複数の項目を追加する`items(Int)`がある。

`List`などの項目のコレクションを追加するための拡張関数も用意されており、簡単に`Column`からマイグレーションできる。

インデックスを提供する`itemsIndexed()`と呼ばれる`items()`を拡張した関数も用意されている。

## Content padding

コンテンツの端周辺にパディングを追加する必要がある場合は、`contentPadding`パラメータに`PaddingValues`を渡すことができる。

## Content spacing

項目間のスペースを追加する場合は`Arrangement.spaceBy()`を利用することができる。

## Item animations

`RecyclerView`で提供されている項目変更時に自動的に適用されるアニメーションは現在はまだ提供されていない。
https://issuetracker.google.com/issues/150812265 で管理されているので状況を知ることはできる。

## Sticky headers(experimental)

Caution： Experimental APIなので変更か完全に削除される可能性がある

`LazyColumn`でSticky Headerを実現するためには、experimental APIである`stickyHeader()`を利用する。

## Grids(experimental)

Caution： Experimental APIなので変更か完全に削除される可能性がある

`LazyVerticalGrid`はグリッド表示を実験的にサポートする。

`cells`パラメータは`GridCells.Adaptive`などを指定し1行にどのようにセルを表示するのかをコントロールする。

1行に表示する数が正確にわかっている場合は`GridCells.Fixed`を利用する。

## Reacting to scroll position

多くのアプリではスクロール位置と項目の変更を監視、反応する必要があり、これらのユースケースは`LazyListState`がサポートしている。

シンプルなユースケースではアプリは一番始めに表示されている項目についての情報が必要になるが、`LazyListState`が`firstVisibleItemIndex`と`firstVisibleItemScrollOffset`プロパティが提供されている。

Note：`derivedStateOf()`は不要な合成を最小限に抑えている。詳細は https://developer.android.com/jetpack/compose/lifecycle#derivedstateof を参照。

ユーザが特定の位置までスクロールしたらアナリティクスイベントを送信するようなcompositionで直接Stateを読み取る必要がないケースでは、`snapshotFlow()`を使用する。

`LazyListState`は`layoutInfo`を通じて画面に現在表示されている全ての情報を提供する。

## Controlling the scroll position

スクロール位置を制御するために、`LazyListState`は即座にスクロール位置を変更する`scrollToItem()`やアニメーションありでスクロールする`animateScrollToItem()`を提供している。

Note： `scrollToItem()`と`animateScrollToItem()`は両方ともsuspend関数なのでCoroutine内で実行する必要がある。Compose内でのCoroutineについては https://developer.android.com/jetpack/compose/kotlin#coroutines を参照。

## Large data-sets(paging)

Paging Libraryは項目数が大きいデータを必要な単位だけ読み込み、表示を有効にする。Paging 3.0以上ではComposeサポートを`androidx.paging:paging-compose`を通じてサポートしている。

Note： Paging 3.0へのマイグレーションは https://developer.android.com/topic/libraries/architecture/paging/v3-migration を参照。

ページングされたコンテンツを表示するために、`collectAsLazyPagingItems()`拡張関数を利用し、それから`items()`で`LazyPagingItems`を渡す。`item`が`null`の場合はデータが読み込まれるまでプレースホルダーを表示することができる。

Warning： `RemoteMediator`を使用してネットワークからデータを取得する場合は、現実的なサイズのプレースホルダーを提供する。`RemoteMediator`を使用する場合、新しいデータを取得するために、画面がコンテンツでいっぱいになるまでに繰り返し起動されるので、画面いっぱいにならないと何ページものデータを取得することになってしまう。

## Item keys

デフォルトでは項目ごとの状態はリスト内の位置がキーとなっている。そのため位置が変わってしまうと状態が失われてしまう。例えば、`LazyColumn`内の`LazyRow`のケースで行が項目の位置を変更するとスクロール位置を失うことになってしまう。

Note： remember state については https://developer.android.com/jetpack/compose/state を参照。

上記の問題を解決するために`key`パラメータで項目ごとのユニークなキーを提供することができる。

Note： keyは`Bundle`内に格納が可能なデータとする必要がある。
