* Changed log file output with `logger=File: <log file path>` to output **from the top** of the file.  
  When outputting by **appending**, specify with `logger=File: +<log file path>`.
* Fixed a bug that throw a `NullPointerException` on the output of an object of a class
  which `<class>.getPackage()` returns `null`.

---
*Japanese*

* `logger=File: <ログファイルパス>` でのログのファイル出力は、ファイルの先頭から出力するようにしました。  
  追記で出力する場合は、`logger=File: +<ログファイルパス>` で指定してください。
* `<クラス>.getPackage()` が `null` を返すクラスのオブジェクトの出力で `NullPointerException` がスローされるバグを修正しました。
