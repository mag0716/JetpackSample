# Compose tooling

https://developer.android.com/jetpack/compose/tooling

Android StudioはJetpack Composeのための機能を数多く提供している。

View-based UIとの基本的な違いは、`View`を使っていない事でこのアプローチにより、Android Studioはエミュレータの起動や端末接続なしで機能を提供できている。View-based UIに比べると、UIデザインの実装を高速に繰り返すことができる。

Android Studioで機能を有効にするためには

```
androidx.compose.ui:ui-tooling
```

に追加する必要がある。

Note: Composeのテンプレートを使ってプロジェクトを作成すれば自動的に追加されている。

## Composable Preview

プレビューを表示するためには、`@Composable`をつけた関数に`@Preview`を追加する。

Android Studioでは、`Split`か`Design`を選択すればAndroid Studio上でプレビューが確認できる。

`@Preview`は表示をカスタマイズするためのパラメータを受け付けている。

### @Preview features

### Interactive mode

Interactive modeはプレビューを操作する事で端末での動作を簡易的に確認させることができる。Interactive modeはサンドボックス環境が独立しており、要素のクリックや入力、アニメーションなども可能で、すばやくComposableの状態や動作を確認するための手段。

端末なしで実行できるが以下の制約がある。

* ネットワーク接続はない
* ファイルアクセスはできない
* `Context` APIは全てをサポートしていない

Note: この昨日は手動で有効化する必要がある。

### Deploy Preview

特定の`@Preview`をエミュレータや端末にデプロイする事ができる。デプロイする場合は新しいActivityとして起動され、`Context`や権限などは共有されるので、すでに許可されていれば権限取得のための実装をする必要はない。

### Code navigation and composable outlines

プレビューをホバーするとアウトラインが表示され内包しているComposableを確認できる。クリックする事で定義にジャンプする。

### Copy @Preview render

プレビューの右クリックでコピー可能

### @Preview configuration

Editorの左側にある設定アイコンをタップすると、`@Preview`のパラメータを変更するダイアログが表示される。

### Set background color

デフォルトではComposableは透明な背景上に表示される。背景を追加するためには、`showBackground`と`backgroundColor`を指定する。

### Dimensions

デフォルトでは自動的に`wrap`が適用される。手動で指定したい場合は、`heightDp`, `widthDp`パラメータを指定する。すでに`Dp`となっているので`.dp`の指定は不要。

### Locale

`locale`でロケールの指定で可能。

### System UI

プレビューにステータスバーやActionBarを表示する必要がある場合は、`showSystemUi`パラメータを指定する。

## Editor actions

Android StudioはJetpack Composeの開発を改善するためにEditor内に機能を持っている。

### Live Templates

Android StudioはComposeに関連する以下のようなlive templatesを持っている。

* `comp`
* `prev`
* `paddp`
* `weight`
* `W`, `WR`, `WC`

### Gutter icons

#### Deploy preview

アイコンタップで指定の`@Preview`をエミュレータや端末にデプロイできる。

#### Color picker

Composableの内外で色を定義している場合、Color pickerが表示され、色をピッカー上で選択できる。

#### Image resource picker

drawable, vector, image など使っている場合はリソースプレビューが表示され、リソースの変更が可能。

## Iterative code development

検査、値の変更、最終結果の確認をフルビルドを必要としていない。

### Live Edit of literals

以下のリテラルの変更はリアルタイムでの更新が可能。

* `Int`
* `String`
* `Color`
* `Dp`
* `Boolean`

### Apply Changes

Apply Changesを利用するといくつかの制約はあるがエミュレータや端末に再デプロイなしに更新することができる。

## Layout Inspector

エミュレータや端末で動作しているアプリの検証が可能になる。

## Animations

Interactive Previewでアニメーションの検証が可能。Composableにアニメーションが記述されている場合、プレビュー上でアニメーションの開始、停止、ループ再生などができる。

Note: この機能は手動で有効化する必要がある。

## Enable experimental features

Android StudioのPreferencesの中にあるExperimentalの項目内で変更する。
