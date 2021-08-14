# Compose and other libraries

https://developer.android.com/jetpack/compose/libraries

## ViewModel

`ViewModel`を利用している場合、Composableからは`viewModel()`関数によって`ViewModel`にアクセスすることができる。

Caution: `viewModel()`を利用するためには`androidx.lifecycle:lifecycle-viewmodel-compose:$latestVersion`への依存関係を追加する必要がある。
最新版は https://developer.android.com/jetpack/androidx/releases/lifecycle#lifecycle_viewmodel_compose_2 を参照

`viewModel()`は与えられたスコープ内で既存の`ViewModel`を返すか、新たに作成する。
Activity内で利用されているComposableで`viewModel()`を利用するとActivityが終了するかプロセスが死ぬまで同じインスタンスが返される。

もし、`ViweModel`に依存していれば、`ViewModelProvider.Factory`をパラメータとして受け取ることもできる。

詳細は https://developer.android.com/jetpack/compose/interop#viewmodel を参照。

## Streams of datas

ComposeにはAndroidでよく利用されるストリームベースのextensionsが用意されており、それぞれ別のアーティファクトによって提供されている。

* `LiveData.observeAsState`:`androidx.compose.runtime:runtime-livedata:$composeVersion`
* `Flow.collectAsState()`:依存関係の追加は不要
* `Observable.subscribeAsState`:`androidx.compose.runtime:runtime-rxjava2:$composeVersion`, `androidx.compose.runtime:runtime-rxjava3:$composeVersion`

これらのアーティファクトはリスナとして登録し、`State`として値を表現する。
新しい値を受け取ると、Composeは`State`を利用しているUIがrecompositionされる。

## Asynchronous operations in Compose

Composableからの非同期処理にCoroutinesが利用されている。

Effects APIの詳細は https://developer.android.com/jetpack/compose/side-effects を参照。

## Navigation

Jetpack Composeを利用したプロジェクトにはNavigationライブラリの利用が推奨されている。
Composable間の遷移にNavigation Component自体の強みや機能を利用することができる。

詳細は https://developer.android.com/jetpack/compose/navigation を参照。

## Hilt

HiltはAndroidアプリでのDIに推奨されており、Composeでも動作する。

`viewModel()`はHiltが`@HiltViewModel`を使用して構築する`ViewModel`を自動的に利用する。
詳細は https://developer.android.com/training/dependency-injection/hilt-jetpack#viewmodels を参照。

### Hilt and Navigation

HiltもCompose Navigationライブラリと統合されている。

もし、`@HiltViewModel`が付けられた`ViewModel`はNavigation Graphのスコープとする場合は、`@AndroidEntryPoint`が付けられたFragmentかActivityで動くComposableで`hiltViewModel`を利用する。

## Paging

Pagingを利用するとデータを段階的に読み込むことが容易になり、Composeでもサポートされている。
`paging-compose` の依存を追加し、バージョンについては https://developer.android.com/jetpack/androidx/releases/paging を参照。

ComposeでPagingを使用する方法については、https://developer.android.com/jetpack/compose/lists を参照。

## Image loading

ネットワークなどの外部から画像を取得、表示するにはAccompanistライブラリを使うのが簡単で、CoilやGlideが統合されている。
