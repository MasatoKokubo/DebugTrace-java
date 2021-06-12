#### Improved the line break handling of data output.

#### Added the following properties specified in DebugTrace.properties.
* `sizeFormat` - Output format of the size of collections and maps (default: `size:%1d`)
* `minimumOutputSize` - Minimum value to output the number of elements of array, collection and map (default: `5`)
* `lengthFormat` - Output format of the length of strings (default: `length:% 1d`)
* `minimumOutputLength` - Minimum value to output the length of string length (default: `5`)
* `maximumDataOutputWidth` - Maximum output width of data (default: `70`)

#### Changed the following property names specified in DebugTrace.properties. However, you can specify the previous names for compatibility.
* `enterFormat` <- `enterString`
* `leaveFormat` <- `leaveString`
* `threadBoundaryFormat` <- `threadBoundaryString`
* `classBoundaryFormat` <- `classBoundaryString`
* `nonOutputString` <- `nonPrintString`
* `collectionLimit` <- `arrayLimit`
* `nonOutputProperties` <- `nonPrintProperties`

#### Delete the following properties specified in DebugTrace.properties.
* `fieldNameValueSeparator` - integrated into `keyValueSeparator`
* `mapLimit` - integrated into `collectionLimit`

---
*Japanese*

#### データ出力の改行処理を改善

#### DebugTrace.propertiesで指定する以下のプロパティを追加
* `sizeFormat` - コレクションおよびマップの要素数の出力フォーマット (デフォルト: `size:%1d`)
* `minimumOutputSize` - 配列、コレクションおよびマップの要素数を出力する最小値 (デフォルト: `5`)
* `lengthFormat` - 文字列長の出力フォーマット (デフォルト: `length:%1d`)
* `minimumOutputLength` - 文字列長を出力する最小値 (デフォルト: `5`)
* `maximumDataOutputWidth` - データの出力幅の最大値 (デフォルト: `70`)

#### DebugTrace.propertiesで指定する以下のプロパティ名を変更 (互換性維持のため従来の名称も指定可能)
* `enterFormat` <- `enterString`
* `leaveFormat` <- `leaveString`
* `threadBoundaryFormat` <- `threadBoundaryString`
* `classBoundaryFormat` <- `classBoundaryString`
* `nonOutputString` <- `nonPrintString`
* `collectionLimit` <- `arrayLimit`
* `nonOutputProperties` <- `nonPrintProperties`

* DebugTrace.propertiesで指定する以下のプロパティを削除
* `fieldNameValueSeparator` - `keyValueSeparator` に統合
* `mapLimit` - `collectionLimit` に統合
