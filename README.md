# AndroidCommonLibrary

Androidでよく使用する基本的な機能を実装するライブラリ。

- **MessageDialogFragment**  
メッセージ表示用ダイアログ


- **SingleSelectDialogFragment**  
選択ダイアログ

- **MultiSelectDialogFragment**  
複数選択ダイアログ

- **Color resource**  
CSS3 Extension Color と Material Color の定義

- **Dimens resource**  
マージンサイズ、文字サイズの定義

## 使い方

build.gradleに下記のコードを追加する

```
repositories {
    maven {
        url 'http://kaoru0164.github.io/AndroidCommonLibrary/repository'
    }
}
dependencies {
    compile 'jp.ktoybox.android.library.common:AndroidCommonLibrary:${version}'
}
```

## 変更履歴


| Version | 日付            | 変更内容               |
| ------- | --------------- | -------------------- |
| 0.1.0   | 2018/04/08 (日) | 基本的なDialogを実装. color,dimensリソースを定義        |
