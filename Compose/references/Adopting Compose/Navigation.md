# Navigation with Compose

https://developer.android.com/jetpack/compose/navigation

Navigation ComponentはJetpack Composeをサポートしており、Navigation Componentsの基盤と機能の利点を活かしてComposable間を遷移することが可能。

Note：

## Setup

`androidx.navigation:navigation-compose:1.0.0-alpha09`

## Getting started

`NavController`はNavigation Componentにおける中心的なAPIでステートフルでアプリの画面を構成するComposableのバックスタックと各画面の状態を記録する。

`NavController`は`rememberNavController`によって生成することができる。

`NavController`は参照が必要なComposableの全てがアクセスできる場所に作成する必要がある。これはstate hoistingの原則に従っており、`NavController`と`currentBackStackEntryAsState()`を通じて提供する状態を画面外のComposableを更新する際の情報として利用することができる。この機能の例として https://developer.android.com/jetpack/compose/navigation#bottom-nav が参考になる。

## Creating a NavHost

`NavController`ごとに1つの`NavHost`と関連づける必要がある。`NavHost`はcomosable desitiantion間の繊維を定義したnavigation graphと`NavController`とリンクされる。`NavHost`は自動的にrecomposeされる。navigation graphに定義されたcomposable desitinationはrouteに関連づけられる。

Key Term： Routeは暗黙的なdeep linkのようなString型で指定されたcomposableへのパスでユニークである必要がある。

`NavHost`の生成には`rememberNavController()`を通じて生成された`NavController`が必要であり、`startDestination`の指定が必要となる。`NavHost`の生成にはNavigation Kotlin DSLのラムダ構文を使用して、navigation graphが構築され、destinationの指定には`composable()`を利用する。

Note： Navigation Componentは[原則](https://developer.android.com/guide/navigation/navigation-principles#fixed_start_destination)に従う必要があり、固定のstaring desitinationを利用する必要がある。`startDestination`にはcomposableを利用するべきではない。

## Navigation to a composable

navigation graph内のcomposable desitinationへの遷移には`navigate()`を利用する必要がある。`navigate()`はdestinationへのrouteを示す1つの`String`型のパラメータを渡す。

`navigate()`は呼び出しごとにrecompositionが走らないようにcomposableの一部ではなくコールバック内でのみ呼び出すべき。

`navigate()`はデフォルトではdestinationをバックスタックに追加するが、オプションによって動作を変えることもできる。

Note: Navigation Composeでは`anim`ブロックによってアニメーションの指定はできない。feature requestは https://issuetracker.google.com/issues/172112072 で参照できる。

## Navigate with arguments

Navigation Composeはcomposable destination間の遷移に引数を渡すこともサポートしている。利用するためにはrouteにdeep linkの引数のようにプレースホルダーを指定する必要がある。

デフォルトでは`String`型としてパースされるが、`type`の指定によって別の型を指定することもできる。

`NavArguments`は`composable()`関数のラムダ内で有効な`NavBackStackEntry`から取得するべき。

destinationへ引数を渡すためにはrouteのプレースホルダーの位置に値を指定する必要がある。

サポートしている型については、https://developer.android.com/guide/navigation/navigation-pass-data#supported_argument_types を参照。

### Adding optional NavArguments

Navigation ComposeではOptionalな引数もサポートしており、2ツノ方法が利用できる。

* `"?argName={argName}"` のようにクエリパラメータ構文を利用する
* `defaultValue`をセットするか、`nullability = true`とする

つまり、`composable()`関数に明示的にOptionalな引数を全て追加する必要がある。

routeを通じて引数を処理する構造はcomposableがNavigationから完全に独立していることを意味し、多くのテストが可能になる。

## Deep links

Navigation Composeでは`composable()`の一部として定義可能な暗黙的Deep Linksをサポートしているおり、`navDeepLink()` を利用する。

これらのDeep Linksは特定のURL、アクションMIME Typeをcomposableに関連づけることができ、デフォルトでは外部アプリには公開されない。外部アプリに公開するためにはマニフェストファイルの定義が必要。

Navigationは他のアプリによってDeep Linksが引き起こされた場合、自動的に該当のComposableにリンクする。

Deep Linksは`pendingIntent`を使って構築することでも利用することができる。

## Nested Navigation

destinationは特定のフローをモジュール化しネストされたNavigation Graph内にグループ化することができる。

`NavHost`にネストされたNavigation Graphを追加する場合は、拡張関数である`navigation()`が利用できる。

## Integration with the bottom nav bar

composableの階層の高いレベルに`NavController`を定義することによって、`BottomNavBar`のような他のコポーネントとNavigationを接続することが可能になる。

`BottomNavBar`のアイテムをNavigation Graphのルートにリンクさせるには、routeを示す文字列と文字列リソースIDを含むsealed classの定義が推奨される。
そして定義したsealed classのobjectを`List`に配置し`BottomNavigationItem`として利用することが可能になる。

`BottomNavigation` composableでは、`currentBackStackEntryAsState()`を使用し`NavBackStackEntry`を取得し、`KEY_ROUTE`で取得できるrouteを取得する。routeを利用すことで選択中かどうかを判断し、ラベルやハイライトを設定する。

`NavController.currentBackStackEntryAsState()`を利用し、`NavHost`から`navController`の状態を取り出し共有することで、`BottomNavigation`は自動的に最新の状態を持つことになるという利点がある。

## Testing

テストできるようにNavigationのコードをcomposable destinationから切り離すことが強く推奨されている。

composalbeのラムダによってNavigationのコードをcomposable自体から分離することを可能にし、以下の2つの方向で機能する。

* パース済みの引数のみcomposableに渡す
* `NavController`自身ではなくcomposableの遷移によって発生すべきラムダを渡す

composableのラムダはNavigationのAPIとcomposable間のギャップを埋めるために必要な最小限のロジックをカプセル化してくれている。

## Learn more

[Get started with the Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
