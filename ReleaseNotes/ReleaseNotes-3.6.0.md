* Fixed a bug where the `print` method could throw a `java.lang.IndexOutOfBoundsException`.
* Added calling source file name and line number to log output of `enter` method.
* Removed deprecated method with `mapName` as an argument.
* Removed deprecated properties in DebugTrace.properties.
* Abolished `logLevel` setting in DebugTrace.properties and made it fixed (`Jdk`: `FINEST`, others: `TRACE`).

---
*Japanese*

* `print` メソッドで `java.lang.IndexOutOfBoundsException` がスローされる事があるバグを修正しました。
* `enter` メソッドのログ出力に、呼び出し元のソースファイル名と行番号を追加しました。 
* `mapName` を引数に持つ非推奨のメソッドを削除しました。
* DebugTrace.properties の非推奨のプロパティを削除しました。
* DebugTrace.properties で `logLevel` の設定を廃止し、固定 (`Jdk`: `FINEST`, それ以外: `TRACE`) にしました。
