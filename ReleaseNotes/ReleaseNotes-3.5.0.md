* Improved the log output of `byte[]`.
* Changed default values for the following properties.

|Property Name|New Default Value|Old Default Value|
|:----|----:|----:|
|`minimumOutputSize`  |16 |5   |
|`minimumOutputLength`|16 |5   |
|`collectionLimit`    |128|512 |
|`byteArrayLimit`     |256|8192|
|`stringLimit`        |256|8192|

* Enabled to specify the package with the `reflectionClasses` property.  
Example: `reflectionClasses = org.debugtrace.example.`

---
*Japanese*

* `byte[]` の出力内容を改善しました。
* 以下のプロパティのデフォルト値を変更しました。

|プロパティ名|新デフォルト値|旧デフォルト値|
|:----|----:|----:|
|`minimumOutputSize`  |16 |5   |
|`minimumOutputLength`|16 |5   |
|`collectionLimit`    |128|512 |
|`byteArrayLimit`     |256|8192|
|`stringLimit`        |256|8192|

* `reflectionClasses` プロパティでパッケージを指定できるようにしました。  
例: `reflectionClasses = org.debugtrace.example.`
