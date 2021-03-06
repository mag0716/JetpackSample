# Navigation

[Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) に関する調査結果

## 対象バージョン

2.2.0-beta01

## TODO

## サンプル

| モジュール名 | 概要 | 作成バージョン |
| --- | --- | --- |
| activitytransition | Activity への遷移を行うサンプル(#2) | 1.0.0-alpha01 |
| app | 各タブのバックスタックを保持するサンプル(#6, #13) | 1.0.0-alpha02 |
| appbarconfiguration | Toolbar と AppBarConfiguration を紐づけて Up Button が表示される画面をカスタマイズする<br>SecondFragment で Up Button が表示されないようにしている | 1.0.0-alpha07 |
| blankdestination | startDestination にレイアウトのない Fragment を利用した(#8)<br/>スプラッシュを想定 | 1.0.0-alpha02 |
| bottomnavigation | 通常の BottomNavigationView の動作(#12)<br/>タブタップではreplaceし直されるので、EditText の内容は復帰しない<br/>バックキーでの遷移では、EditText の内容が復帰する | 1.0.0-alpha02 |
| bottomsheetdialog | BottomSheetDialogFragment 内の NavigationView と Navigation Graph を連動させる<br/>画面遷移だけでは BottomSheetDialogFragment は非表示にはならず、バックキーで戻ってくると表示された状態となっている(#48) | 1.0.0-alpha06 |
| nestednavigation | conditional navigation の動作<br/>Profile 画面で未ログインだったら Login 画面へ遷移する。(#5) | 1.0.0-alpha02 |
| onnavigationuplistener | [WIP] navigationUp をカスタマイズ<br/>ユースケースが思いつかなかったので OnNavigationUpListener#onNavigateUp が呼び出される条件のみ調査(#60) | 1.0.0-alpha08 |
| safeargs | Safe Args Plugin のサンプル(#33) | 1.0.0-alpha03 |
| sharedelementtransition | [WIP] Fragment 間の遷移時に Shared Element Transition を利用するサンプル<br/>RecyclerView のセルからの遷移は上手くいかない | 1.0.0-alpha06 |
| toolbar | Toolbar 単体で利用する Activity のサンプル(#33)<br/>argument を Toolbar のタイトルに利用している(#58) | 1.0.0-alpha03 |
| dialog | Dialog への遷移、Dialog から遷移するサンプル(#24) | 2.1.0-beta02 |
| navgraphviewmodels | Navigation Graph で共有する ViewModels (#23)<br/>2.2.0 から SavedStateHandle にも対応(#27) | 2.1.0-rc01 |

### app

* https://github.com/googlesamples/android-architecture-components/tree/master/NavigationAdvancedSample を参考
* 各タブ毎のバックスタックを制御し、タブ切り替えでバックスタックをクリアしない
* メニューの ID と各タブで利用する Navigation Graph のファイル名を同じにする必要がある

#### TODO

* どのタブにいても、Toolbar のメニューから SettingActivity に遷移させたいが、同じ定義が各 Navigation Graph に存在していて気持ち悪い

## 更新履歴

https://developer.android.com/jetpack/androidx/releases/navigation

### [2.2.0-beta01](https://developer.android.com/jetpack/androidx/releases/navigation#2.2.0-beta01)

* `NavDestination` のサブクラスで `toString` をオーバーライドできるようになった
* マッチしない deep link を無視するようになった

### [2.2.0-alpha03](https://developer.android.com/jetpack/androidx/releases/navigation#2.2.0-alpha03)

* `setGraph` の後に `setViewModelStore` を呼び出したら `IllegalStateException` になるように変更された

### [2.2.0-alpha02](https://developer.android.com/jetpack/androidx/releases/navigation#2.2.0-alpha02)

* deep link のパラメータのデフォルト値や nullable にサポート
* `NavController.getBackStackEntry()` でバックスタックの ID や NavigationGraph を渡せるようになった

### [2.2.0-alpha01](https://developer.android.com/jetpack/androidx/releases/navigation#2.2.0-alpha01)

* `SavedStateViewModelFactory` がデフォルトで利用されるようになった
  * Navigation Graph スコープの ViewModel が SavedState をサポートしてくれる
* `NavController#getViewModelStore()` が deprecated になった

### 2.1.0-beta02

* 2.1.0-beta01 で発生していた jacoco への依存を削除

### 2.1.0-beta01

`org.jacoco:org.jacoco.agent:0.8.3` に意図せず依存してビルドに失敗する

#### New features

* `NavigationUI#setupWithNavController()` を利用した場合に Up ボタンのアニメーションが追加された

### 2.1.0-alpha06

#### New features

* `app:navGraph` が navigation-runtime に移動

#### API changes

* `NavController#getViewModelStore()` が deprecated になり、`getViewModelStoreOwner()` が追加された
* `<dialog>` のような destination は `FloatingWindow` interface が生成され、AppBar　からの操作では無視される

#### Behavior changes

* `<dialog>` destination の場合に状態を正しく保持できるようになったので、自動でダイアログをポップするようになった

### 2.1.0-alpha05

#### API changes

* `NavController` が `NavController`, `NavHostController` に分かれた
* `setHostOnBackPressedDispatcherOwner` が `setOnBackPressedDispatcher` に置き換えられた
* `setHostOnBackPressedDispatcherOwner` が返していた `NavHostONBackPressedManager` を置き換える `enableOnBackPressed` が追加された

### 2.1.0-alpha04

Bug fixes のみ
2.1.0-alpha03 の Known Issues が直っている

### 2.1.0-alpha03

#### Known Issues

* `app:defaultNavHost="false"` にしていても、バックキーをインターセプトしてしまう

#### New features

* `<dialog>` desitination
* `<deepLink>` を利用して navigate で遷移できるように

#### Behavior changes

* アニメーションが 400ms から 220ms に短縮された

#### API Changes

* `NavHostFragment#createFragmentNavigator()` が deprecated になり `onCreateNavController()` が追加された
* `NavDestination#hasDeepLink()` が追加された

### 2.1.0-alpha02

#### New features

* `by navgraphViewModels()` で Navigation Graph ごとのスコープの ViewModel を取得できる

#### API Changes

* `<activity>` desitination に `app:targetPackage` を定義できるようになった
  * `app:targetPackage="${appliationId}"` で自身のパッケージを指定できる

### 2.1.0-alpha01

#### Dependency changes

* 依存している core, fragment, material のバージョンが更新、legacy-support-core-utils への依存を削除(https://issuetracker.google.com/issues/128632612)

#### API Changes

* `Navigation.crateNavigatinoOnClickListener(NavDirections)` が追加された(https://issuetracker.google.com/issues/127631752)
  * ID, Bundle の引数を持つメソッドのオーバーロード
* `FragmentNavigator.instantiateFragment` が deprecated になった(https://issuetracker.google.com/issues/119054429)
  * 内部実装の変更のみ。動作に変化はない

#### Bug Fixes

* `android:defaultValue="@null"` を指定している時に引数を渡しても null を送れなかった不具合を修正(https://issuetracker.google.com/issues/128531879)
* KotlinPoet のバージョンが上がり、長いパッケージ名が扱えるようになった(https://issuetracker.google.com/issues/123654948)

### 2.0.0

2.0.0-rc02 のバージョン違い

### 2.0.0-rc2

1.0.0-rc02 の AndroidX 対応版

### 1.0.0-rc02

Bug Fixes のみ

### 1.0.0-rc01

Bug Fixes のみ

### 1.0.0-beta02

#### New Features

* reference の defaultValue に 0 を使えるようになった(https://issuetracker.google.com/issues/124248602)

#### Behavior changes

* `.*` が指定されている Deep Link が優先されるようになった(https://issuetracker.google.com/issues/123969518)

### 1.0.0-beta01

#### Behavior changes

* argType で指定した型のみしか defaultValue に指定できないようになった(https://issuetracker.google.com/issues/123551990)
* Safe Args が Android Gradle Plugin 3.3.0 に依存するようになった(https://android-review.googlesource.com/c/platform/frameworks/support/+/888413/)
* Safe Args が Kotlin 1.3.20 に依存するようになった(https://android-review.googlesource.com/c/platform/frameworks/support/+/888414/)

### 1.0.0-alpha11

#### Bug Fixes

* 1.0.0-alpha10 の不具合の hotfix(https://issuetracker.google.com/issues/123307342)

### 1.0.0-alpha10

#### Known Issues

* Global Actions の Directions の import に失敗する(https://issuetracker.google.com/issues/123307342)

#### New Features

* 遅延取得するために `by navArgs()` が追加(https://issuetracker.google.com/issues/122603367)
* Safe Args が生成する Kotlin のコードが `androidx.navigation.safeargs.kotlin` が利用されるようになった(https://issuetracker.google.com/issues/110263087)

#### Behavior Changes

* Deep Link で期待しない Destination に遷移する不具合を修正(https://issuetracker.google.com/issues/118393029)
* `setGraph()` でバックスタックがリセットされるようになった(https://issuetracker.google.com/issues/111450672)
* 不明な Deep Link のときに `IllegalStateException` が throw されずに無視されるようになった(https://issuetracker.google.com/issues/121340440)

#### Breaking Changes

* `NavOptions.applyPopAnimationsToPendingTransition()` が `ActivityNavigator` に移動した(https://issuetracker.google.com/issues/122413117)
* Safe Args が同一の引数を定義できなくなった(https://issuetracker.google.com/issues/123233147)
* Safe Args が Directions のコンストラクタを生成しなくなった(https://issuetracker.google.com/issues/123031660)
* Safe Args が　NavDirections のコンストラクタを生成しなくなった(https://issuetracker.google.com/issues/122963206)
* NavDirections の getArgument() に @NonNull が指定された(https://issuetracker.google.com/issues/123243957)

### 1.0.0-alpha09

* https://developer.android.com/jetpack/docs/release-notes#december_18_2018
* `android.arch.navigation:navigation-testing` の開発が終了
* テストに関するドキュメントが増えた
* https://www.youtube.com/watch?v=2k8x8V77CrU

#### New Features

* `menuCategory="secondary"` が `NavigationUI` のメソッド経由でポップされなくなった
* `AppBarConfiguration` に `OnNavigateUpListener` にセットできるようになった

#### Breaking Changes

* `argType="reference"` を指定した `<argument>` の利用時に reference をパースせずに、ID が提供されるようになった
* `onNavDestinationSelected`  がデフォルトで start destination がポップされるようになった
    * MenuItem のポップを避けるために、`menuCategory="secondary"` が追加された
* `fromBundle` が生成する `Args` が `@NonNull` になった

#### Bug Fixes

* deep link から 正しい `argType` でパースされるようになった(https://issuetracker.google.com/issues/110273284)
* `Navigation` が public になった(https://issuetracker.google.com/issues/121059552)
* Safe Args が Android Gradle Plugin 3.4 Canary 4 以上が必須になった(https://issuetracker.google.com/issues/119662045)

### 1.0.0-alpha08

* https://developer.android.com/jetpack/docs/release-notes#december_6_2018

#### New Features

* `NavigationUI` のメソッド利用時に自動的に `{argName}` が `android:label` のアクァリに使われるようになった
    * Toolbar のタイトルを動的に変更できる
* Support Library 28.0.0 に依存するように変更

#### Breaking Changes

* `OnNavigatedListener` -> `OnDestinationChangedListener`(https://issuetracker.google.com/issues/118670572)
* `OnDestinationChangedListener` に `Bundle` が渡ってくるようになった(https://android-review.googlesource.com/c/platform/frameworks/support/+/837142/)
* `clearTask`, `launchDocument` は削除される(https://issuetracker.google.com/issues/119628354)
    * `popUpTo` を使う
* `ActivityNavigator.Extras` がビルダーパターンを利用するようになり、`Intent.FLAG_ACTIVITY_*` をセットできるようになった(https://android-review.googlesource.com/c/platform/frameworks/support/+/828140/)
* `NavController.onHandleDeepLink` が `handleDeepLink` にリネーム
* サブクラス化する必要のないクラスやメソッドに `final` がついた(https://android-review.googlesource.com/c/platform/frameworks/support/+/835681/)
* deprecated だった `NavHostFragment.setGraph()` が削除
* deprecated だった `NavigationUI.navigateUp()` が削除
* Fragment の生成が `FragmntNavigator` に移動
* `NavGraphNavigator` のコンストラクが `Context` が不要になった
* `NavigatorProvider` が interface から class になって、`SimpleNavigatorProvider` がデフォルト実装になった
* `NavDestination.navigate` が削除され、`Navigator.navigate` に変更された
* `Navigator` がリファクタリングされた
    * ? `OnNavigatorNavigatedListener` が不要になった
    * `navigate` が `NavDestination` が返却されるようになった
* `Navigator` が `NavController` に pop イベントを送信しなくなった
    * バックキーをハンドリングしたければ、`OnBackPressedCallback` を利用する

#### Bug Fixes

* `<navigation>` 要素が destination の時に `popUpTo` が動作するようになった(https://issuetracker.google.com/issues/116831650)
* `IllegalArgumentException` が発生する不具合の修正
* `<activity>` の `dataPattern` に渡す String 以外のデータが `toString` から生成されるようになった(https://issuetracker.google.com/issues/120161365)

#### Safe Args

* Serializable objects、Enum をサポート
* 配列をサポート
* リソースディレクトリのサブフォルダーを無視するようになった
* `@Override` が追加された

### 1.0.0-alpha07

* https://developer.android.com/jetpack/docs/release-notes#october_29_2018

#### New Features

* AppBarConfiguration が追加
  * https://developer.android.com/topic/libraries/architecture/navigation/navigation-ui の AppBarConfiguration の章を参照
  * アップボタンの表示・非表示処理が固定されていたので、カスタマイズが可能となるように追加された(https://issuetracker.google.com/issues/117333663)
* Navigation Graph を引数で渡せるようになった
  * 最初の Fragment にデータを渡すために修正された(https://issuetracker.google.com/issues/110300470)
* カスタムスキーム用に Deep Link で `.`, `-`, `+` がサポートされた
  * https://issuetracker.google.com/112806402

#### Breaking Changes

* navigation-testing-ktx は navigation-testing に含まれ公開されなくなる
* navigation-testing は Kotlin standard library に依存するようになった
* `NavController.setMetadataGraph`, `NavInflater.inflateMetadataGraph` が削除
  * 複雑な Graph を設定することが可能なので悪い状況を引き起こす可能性があるのが理由(https://issuetracker.google.com/issues/118355937)
* `<activity>` desitinations に `<action>` を設定できなくなった
  * Issue Tracker の参照権限がなかったので理由は不明

#### Bug Fixes

* Deep Link のクエリを正しくパースするようになった(https://issuetracker.google.com/issues/110057514)
* Activity の遷移アニメーションが正しく適用されるようになった(https://issuetracker.google.com/issues/117145284)
* カスタム Navigation 利用時の画面回転での強制終了の修正(https://issuetracker.google.com/issues/110763345)

#### Safe Args

* Android Gradle Plugin 3.2.1 に依存するようになった
  * Android Studio 3.3 だと Sync に失敗する(https://issuetracker.google.com/issues/113167627)
* Directions が inner クラスで生成できるようになった(https://issuetracker.google.com/issues/117407555)
  * 例：`android:name="com.example.OnboardingActivity$OnboardingStartFragment`
* `<include>` での Directions の生成が修正(https://issuetracker.google.com/issues/116542123)

### 1.0.0-alpha06

* https://developer.android.com/jetpack/docs/release-notes#september_20_2018

#### New Features

* Shared Element Transitions に対応(https://developer.android.com/topic/libraries/architecture/navigation/navigation-implementing#shared-element)
  * `FragmentNavigator.Extras`
  * `ActivityNavigator.Extras`
* BottomSheetDialogFragment に対応(https://issuetracker.google.com/issues/112158843)
  * 実際には `NavigationView` と Navigation Graph を連動させる

#### API Changes

* [Breaking Change] navigate にパラメータが追加
* `getGraph` が `NonNull` になった

#### Bug Fixes

* `NavigationUI.setupWithNavController` のリーク(https://issuetracker.google.com/issues/111961977)
* `onSaveState` が複数回呼ばれていた不具合が修正(https://issuetracker.google.com/issues/112627079)

#### Safe Args

* Navigation が親の Navigation を継承するようになった(https://issuetracker.google.com/issues/79871405)
* `toString` が実装された(https://issuetracker.google.com/issues/111843389)

### 1.0.0-alpha05

* https://developer.android.com/jetpack/docs/release-notes#august_10_2018

#### Bug Fixes

* バックキー遷移でバックスタックが不正になる(https://issuetracker.google.com/issues/111907708)
* Args の equals が不正(https://issuetracker.google.com/issues/111450897)
* git でブランチの切り替えをするとSafe Args でのビルドに失敗する(https://issuetracker.google.com/issues/109409713)
* ドットが含まれた ID だと Safe Args のビルドに失敗する(https://issuetracker.google.com/issues/111602491)
* Safe Args の nullability に関するエラーメッセージが変更
* `@Nullable` が正しく追加されるようになる

### 1.0.0-alpha04

#### API / Behavior Changes

* `NavHostFragment` が現在の Fragment を primary Fragment として扱う様になった(https://issuetracker.google.com/issues/111345778)
  * これで Fragment がネストされていても、バックキーが ChildFragmentManager に伝搬するようになった

#### Safe Args

* [Breaking Change] `app:type` は他のライブラリとコンフリクトしやすいので `app:argType` に変更された(https://issuetracker.google.com/issues/111110548)
* Android Studio に表示される Safe Args Plugin のエラーメッセージがクリック可能になった(https://issuetracker.google.com/issues/111534438)
* NonNull な引数に null を代入可能だった(https://issuetracker.google.com/issues/111451769)
* Safe Args Plugin で生成されるメソッドに `@NonNull` が指定されるようになった(https://issuetracker.google.com/issues/111455456)

#### Bug Fixes

* Deep Link からの起動で、バックキーで Toolbar が更新されない不具合(https://issuetracker.google.com/issues/111515685)

### 1.0.0-alpha03

#### API / Behavior Changes

* `NavigationUI.setupWithNavController` で `Toolbar`, `CollapsingToolbarLayout` に対応
  * `ActionBar` として、`Toolbar` が利用されていないとタイトルなどが反映されなかったのを、`Toolbar`　単体として利用しても、反映される様になった
  * `DrawerLayout` も同時に渡すことができる
  * `OnNavigatedListener`
  * `ActionBarOnNavigatedListener` の実装が変更された
    * 直接、`NavController.OnNavigatedListener` を実装していたのを、`AbstractAppBarOnNavigatedListener` が新規作成され、その抽象クラスを継承する様になった
      * `setTitle`, `setNavigationIcon` が抽象化された
  * `AbstractAppBarOnNavigatedListener` を継承した、`ToolbarOnNavigatedListener`, `CollapsingToolbarOnNavigatedListener` が追加され、`setupWithNavController` で利用される様になった
* バックスタックが空だったりバックスタックにない destination の ID が渡されたりした場合は、`popBackStack` が false を返却する様になった
  * `FragmentNavigator#popBackStack` の実装が大きく変わった
* `onSaveInstanceState` 後は、`FragmentNavigator` は遷移処理を無視する様になった
  * `FragmentNavigator#navigate` で遷移処理前に、`FragmentManager#isStateSaved` をチェックする様になった

#### Safe Args

* [Breaking Change] アルファベットか数字以外はキャメルケースに変換される様になった
* [Breaking Change] 引数はデフォルトは NonNull だが、`app:nullable` で null を渡せる様になった
* `app:type` に `long` が追加された
* `Parcelable` な引数がサポートされた。デフォルト値は `@null` のみ
* `NavDirections` に `equals` と `hashCode` が実装された
* ライブラリプロジェクトに適用できる様になった
* feature プロジェクトに適用できる様になった

#### Bug Fixes
