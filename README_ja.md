DebugTrace-java
===========

DebugTrace-java は、Java プログラムのデバッグ時にトレースログを出力するライブラリで、Java 8 以降 で利用できます。  
メソッドの開始と終了箇所に `DebugTrace.enter()` および `DebugTrace.leave()` を埋め込む事で、開発中の Java  プログラムの実行状況を把握する事ができます。

### 1. 特徴

- コール元のクラス名、メソッド名、ソースファイルおよび行番号を自動的に出力。
- メソッドやオブジェクトのネストで、ログを自動的にインデント。
- スレッドの切り替え時に自動的にログを出力。
- `toString` メソッドを実装していないクラスのオブジェクトでもリフレクションを使用して内容を出力。
- DebugTrace.properties で、出力内容のカスタマイズが可能。
- 実行時に依存するライブラリがない。(下記ロギング・ライブラリを使用する場合は必要)
- 各種ロギング・ライブラリを使用可能。
    - JDKロガー
    - Log4j
    - Log4j2
    - SLF4J
    - コンソール (stdout および stderr)

### 2. 使用方法

デバッグ対象のメソッドに対して以下を行います。

1. 対象のメソッドの先頭に `DebugTrace.enter()` を挿入する。
1. 対象のメソッドの終了 (return 文の直前) に `DebugTrace.leave()` を挿入する。
1. 対象のメソッドに引数があれば、その内容をログに出力する `Debug.print(...)` を挿入する。
1. 対象のメソッドに return 文があれば、その内容をログに出力する Debug.print(...) を挿入する。

以下は、DebugTrace のメソッドを使用した Java の例とそれを実行した際のログです。

```java:Example1.java
// Java ソース例
package example;

import java.lang.reflect.Array;
import org.debugtrace.DebugTrace;

public class Example1 {
  // main
  public static void main(String[] args) {
    DebugTrace.enter(); // 追加

    @SuppressWarnings("unused")
    Point[] points = newArray(Point.class, 2);

    DebugTrace.leave(); // 追加
  }

  // 配列を作成
  public static <E> E[] newArray(Class<E> elementType, int length) {
    DebugTrace.enter(); // 追加
    DebugTrace.print("elementType", elementType); // 追加
    DebugTrace.print("length", length); // 追加
    @SuppressWarnings("unchecked")
    E[] array = (E[])Array.newInstance(elementType, length);
    DebugTrace.print("1 array", array); // 追加
    try {
      for (int index = 0; index < length; ++index)
        array[index] = elementType.getConstructor().newInstance();
    }
    catch (RuntimeException e) {throw e;}
    catch (Exception e) {throw new RuntimeException(e);}
    DebugTrace.print("2 array", array); // 追加
    DebugTrace.leave(); // 追加
    return array;
  }

  // Point クラス
  public static class Point {
    private int x;
    private int y;
    public Point() {
    }
    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
    public int getX() {return x;}
    public int getY() {return y;}
  }
}
```

```log:debugtrace.log
2017-07-29 14:02:37.798 DebugTrace 2.4.2 / logger: org.debugtrace.logger.Std$Out
2017-07-29 14:02:37.798 
2017-07-29 14:02:37.798 ______________________________ main ______________________________
2017-07-29 14:02:37.798 
2017-07-29 14:02:37.813 enter example.Example1.main (Example1.java:15)
2017-07-29 14:02:37.813 | enter example.Example1.newArray (Example1.java:25)
2017-07-29 14:02:37.813 | | elementType = (Class)class example.Example1$Point (Example1.java:26)
2017-07-29 14:02:37.813 | | length = 2 (Example1.java:27)
2017-07-29 14:02:37.813 | | 1 array = (example.Example1.Point[] length:2)[
2017-07-29 14:02:37.813 | |   null,
2017-07-29 14:02:37.813 | |   null,
2017-07-29 14:02:37.813 | | ] (Example1.java:30)
2017-07-29 14:02:37.813 | | 2 array = (example.Example1.Point[] length:2)[
2017-07-29 14:02:37.813 | |   (example.Example1.Point)[
2017-07-29 14:02:37.813 | |     x: 0,
2017-07-29 14:02:37.813 | |     y: 0,
2017-07-29 14:02:37.813 | |   ],
2017-07-29 14:02:37.813 | |   (example.Example1.Point)[
2017-07-29 14:02:37.813 | |     x: 0,
2017-07-29 14:02:37.828 | |     y: 0,
2017-07-29 14:02:37.828 | |   ],
2017-07-29 14:02:37.828 | | ] (Example1.java:37)
2017-07-29 14:02:37.828 | leave example.Example1.newArray (Example1.java:38)
2017-07-29 14:02:37.828 leave example.Example1.main (Example1.java:20)
```

### 3. メソッド一覧

このライブラリには以下のメソッドがあります。すべて org.debugtrace.DebugTrace クラスの静的メソッドで、戻り値なしです。

|メソッド名|引 数|処理概要|
|:--|:--|:--|
|enter|なし|メソッドの開始をログに出力する|
|leave|なし|メソッドの終了をログに出力する|
|print|`message`: メッセージ|メッセージをログに出力する|
|print|`messageSupplier`: メッセージのサプライヤー|サプライヤーからメッセージを取得してログに出力する|
|print|`name`: 値の名前<br>`value`: 値|`<値の名前> = <値>`<br>の形式でログに出力する<br>value のタイプは `boolean`, `char`, `byte`, `short`, `int`, `long`, `float`, `double` または `Object`|
|print|`name`: 値の名前<br>`valueSupplier`: 値のサプライヤー|サプライヤーから値を取得して<br>`<値の名前> = <値>`<br>の形式でログに出力する<br>valueSupplier のタイプは `BooleanSupplier`, `IntSupplier`, `LongSupplier` または `Supplier<T>`|
|print<br>*(v2.4.0~)*|`mapName`: 数値に対応する定数名を得るためのマップの名前<br>`name`: 値の名前<br>`value`: 出力値|`<値の名前> = <値>(<定数名>)`<br>の形式でログに出力する<br>value のタイプは `byte`, `short`, `int`, `long` または `Object`|
|print<br>*(v2.4.0~)*|`mapName`: 数値に対応する定数名を得るためのマップの名前<br>`name`: 値の名前<br>`valueSupplier`: 値のサプライヤー|サプライヤーから値を取得して<br>`<値の名前> = <値>(<定数名>)`<br>の形式でログに出力する<br>valueSupplier のタイプは `IntSupplier`, `LongSupplier` または `Supplier<T>`|

### 4. *DebugTrace.properties* ファイルのプロパティ一覧

DebugTrace は、クラスパスにある `DebugTrace.properties` ファイルを起動時に読み込みます。
`DebugTrace.properties` ファイルには以下のプロパティを指定できます。

|プロパティ名|設定する値|デフォルト値|
|:--|:--|:--|
|logger| DebugTrace が使用するロガー<br>`Jdk`: JDK ロガー を使用<br>`Log4j`: Log4j を使用<br>`Log4j2`: Log4j2 を使用<br>`SLF4J`: SLF4J を使用<br>`Std$Out`: stdout へ出力<br>`Std$Err`: stderr へ出力|`Std$Out`|
|logLevel|Log level<br>`default` : 各ロガーでの最低レベル<br>JDK Logger 使用時は `finest`, `finer`, `fine`, `config`, `info`, `warning`, `severe` のいずれか<br>Log4j または Lo4j2 使用時は `trace`, `debug`, `info`, `warn`, `error`, `fatal` のいずれか<br>SLF4J 使用時は `trace`, `debug`, `info`, `warn`, `error` のいずれか<br>|JDK Logger: `finest`<br>Log4j/Lo4j2/SLF4J: `trace`|
|enterString|enter メソッドでの出力文字列<br><br>パラメータ:<br>`%1`: 呼出側のクラス名<br>`%2`: 呼出側のメソッド名<br>`%3`: 呼出側のファイル名<br>`%4`: 呼出側の行番号|`Enter %1$s.%2$s (%3$s:%4$d)`|
|leaveString|leave メソッドでの出力文字列<br><br>パラメータ:<br>`%1`: 呼出側のクラス名<br>`%2`: 呼出側のメソッド名<br>`%3`: 呼出側のファイル名<br>`%4`: 呼出側の行番号|`Leave %1$s.%2$s (%3$s:%4$d)`|
|threadBoundaryString|スレッド境界で出力される文字列<br><br>パラメータ:<br>`%1`: スレッド名|`______________________________ %1$s ______________________________`|
|classBoundaryString|クラス境界での出力文字列<br><br>パラメータ:<br>`%1`: クラス名|`____ %1$s ____`|
|indentString|コードのインデント文字列<br>`"\\s"` は空白文字1つに置き換える|`|\\s`|
|dataIndentString|データのインデント文字列|`\\s\\s`|
|limitString|制限を超えた場合の出力文字列|`...`|
|nonPrintString<br>*(v1.5.0~)*|出力しないプロパティ値の文字列|`***`|
|cyclicReferenceString|循環参照時の出力文字列|`\\s* cyclic reference *\\s`|
|varNameValueSeparator|変数名と値のセパレータ|`\\s=\\s`|
|keyValueSeparator|マップのキーと値のセパレータ|`:\\s`|
|fieldNameValueSeparator|フィールド名と値のセパレータ|`:\\s`|
|printSuffixFormat|print メソッドで付加される文字列<br><br>パラメータ:<br>`%1`: 呼出側のクラス名<br>`%2`: 呼出側のメソッド名<br>`%3`: 呼出側のファイル名<br>`%4`: 呼出側の行番号|`\\s(%3$s:%4$d)`|
|utilDateFormat|java.util.Date の出力フォーマット<br><br>パラメータ:<br>`%1`: java.util.Date オブジェクト<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS`|
|sqlDateFormat|java.sql.Date の出力フォーマット<br><br>パラメータ:<br>`%1`: java.sql.Date オブジェクト|`%1$tY-%1$tm-%1$td`|
|timeFormat|java.sql.Time の出力フォーマット<br><br>パラメータ:<br>`%1`: java.sql.Time オブジェクト<br>|`%1$tH:%1$tM:%1$tS`|
|timestampFormat|Output format of java.sql.Timestamp<br><br>パラメータ:<br>`%1`: java.sql.Timestamp オブジェクト<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL`|
|arrayLimit|配列とCollection 要素の出力数の制限|512|
|byteArrayLimit|バイト配列 (byte[]) 要素の出力数の制限|8192|
|mapLimit|Map 要素の出力制限数|512|
|stringLimit|String の出力文字数の制限|8192|
|nonPrintProperties<br>*(v2.2.0~)*|出力しないプロパティのリスト<br><br>値のフォーマット:<br>`<フルクラス名>#<プロパティ名>,`<br>`<フルクラス名>#<プロパティ名>,`<br>`...`|*\<空リスト\>*|
|defaultPackage<br>*(v2.3.0~)*|使用する Java ソースのデフォルトパッケージ|*\<なし\>*|
|defaultPackageString<br>*(v2.3.0~)*|デフォルトパッケージ部を置き換える文字列|`...`|
|reflectionClasses<br>*(v2.4.0~)*|toString メソッドを実装していてもリフレクションで内容を出力するクラス名のリスト|*\<空リスト\>*|
|mapNameMap<br>*(v2.4.0~)*|変数名に対応するマップ名を取得するためのマップ<br><br>値のフォーマット:<br>`<変数名>:<マップ名>,`<br>`<変数名>:<マップ名>,`<br>`...`|*\<空マップ\>*|
|<マップ名><br>*(v2.4.0~)*|数値 (key) と数値に対応する定数名(value)のマップ<br><br>値のフォーマット:<br>`<数値>:<定数名>,`<br>`<数値>:<定数名>,`<br>`...`|以下のマップ名が定義済み<br>`Calendar`: Calendar.ERA など<br>`CalendarWeek`: Calendar.SUNDAY など<br>`CalendarMonth`: Calendar.JANUARY など<br>`CalendarAmPm`: Calendar.AM など<br>`SqlTypes`: java.sql.Types.BIT など|

#### 4.1. nonPrintProperties, nonPrintString

DebugTrace は、`toString` メソッドが実装されていない場合は、リフレクションを使用してオブジェクト内容を出力します。
他のオブジェクトの参照があれば、そのオブジェクトの内容も出力します。
ただし循環参照がある場合は、自動的に検出して出力を中断します。  
`nonPrintProperties` プロパティを指定して出力を抑制する事もできます。
このプロパティの値は、カンマ区切りで複数指定できます。  
`nonPrintProperties` で指定されたプロパティの値は、`nonPrintString` で指定された文字列 (デフォルト: `***`) で出力されます。

```properties:DebugTrace.properties
# nonPrintProperties の例
nonPrintProperties = \
    org.lightsleep.helper.EntityInfo#columnInfos,\
    org.lightsleep.helper.EntityInfo#keyColumnInfos,\
    org.lightsleep.helper.ColumnInfo#entityInfo
```

#### 4.2. 定数マップ, mapNameMap

定数マップは、キーが数値で値が定数名のマップです。このプロパティのキー (マップ名) を `print` メソッドの引数にしてコールすると数値に定数名が付加されて出力されます。

```properties:DebugTrace.properties
# 定数マップの例
AppleBrand = \
    0: Apple.NO_BRAND,\
    1: Apple.AKANE,\
    2: Apple.AKIYO,\
    3: Apple.AZUSA,\
    4: Apple.YUKARI
```

```java
// Java ソースの例
int appleBrand = Apple.AKANE;
DebugTrace.print("AppleBrand", "appleBrand", appleBrand);
```

```log
// Log の例
2017-07-29 13:45:32.489 | appleBrand = 1(Apple.AKANE) (README_example.java:29)
```

変数名に対応するマップ名を mapNameMap プロパティで指定すると、マップ名を指定しない場合でも定数名が出力されます。

```properties:DebugTrace.properties
# mapNameMap の例
mapNameMap = appleBrand: AppleBrand
```

```java
// Java ソースの例
int appleBrand = Apple.AKANE;
DebugTrace.print("appleBrand", appleBrand);
appleBrand = Apple.AKIYO;
DebugTrace.print(" 2 appleBrand ", appleBrand);
appleBrand = Apple.AZUSA;
DebugTrace.print(" 3 example.appleBrand ", appleBrand);
appleBrand = Apple.YUKARI;
DebugTrace.print(" 4 example. appleBrand ", appleBrand);
```

```log
// Log の例
2017-07-29 13:45:32.489 | appleBrand = 1(Apple.AKANE) (README_example.java:38)
2017-07-29 13:45:32.489 |  2 appleBrand  = 2(Apple.AKIYO) (README_example.java:40)
2017-07-29 13:45:32.489 |  3 example.appleBrand  = 3(Apple.AZUSA) (README_example.java:42)
2017-07-29 13:45:32.489 |  4 example. appleBrand  = 4(Apple.YUKARI) (README_example.java:44)
```

### 5. ロギング・ライブラリの使用例

ロギング・ライブラリを使用する際の DebugTrace のロガー名は、`org.debugtrace.DebugTrace` です。

#### 5-1. logging.properties (JDK標準) の例

```properties:logging.properties
# logging.properties
handlers = java.util.logging.ConsoleHandler
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
java.util.logging.SimpleFormatter.format = [Jdk] %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %5$s%n
java.util.logging.ConsoleHandler.level = FINEST
org.debugtrace.DebugTrace.level = FINEST
```
*Java 起動時オプションとして `-Djava.util.logging.config.file=<パス>/logging.properties` が必要*

#### 5-2. log4j.xml (Log4j) の例

```xml:log4j.xml
<!-- log4j.xml -->
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/" debug="false">
  <appender name="Console" class="org.apache.log4j.ConsoleAppender">
    <param name="Target" value="System.out"/>
    <layout class="org.apache.log4j.PatternLayout">
      <param name="ConversionPattern" value="[Log4j] %d{yyyy-MM-dd HH:mm:ss.SSS} %5p %m%n"/>
    </layout>
  </appender>
  <logger name="org.debugtrace.DebugTrace">
    <level value ="trace"/>
    <appender-ref ref="Console"/>
  </logger>
</log4j:configuration>
```

#### 5-3. log4j2.xml (Log4j2) の例

```xml:log4j2.xml
<!-- log4j2.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<configuration status="WARN">
  <appenders>
    <Console name="Console" target="SYSTEM_OUT">
      <PatternLayout pattern="[Log4j2] %d{yyyy-MM-dd HH:mm:ss.SSS} %5p %msg%n"/>
    </Console>
  </appenders>
  <loggers>
    <logger name="org.debugtrace.DebugTrace" level="trace"/>
    <root level="error"><appender-ref ref="Console"/></root>
  </loggers>
</configuration>
```

#### 5-4. logback.xml (SLF4J / Logback) の例

```xml:logback.xml
<!-- logback.xml -->
<configuration>
  <appender name="Console" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>[SLF4J logback] %d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %msg%n</pattern>
    </encoder>
  </appender>
  <logger name="org.debugtrace.DebugTrace" level="trace"/>
  <root level="error"><appender-ref ref="Console"/></root>
</configuration>
```

### 6. build.gradle の記述例

```gradle:build.gradle
// build.gradle
repositories {
    jcenter()
}

dependencies {
    compile 'org.debugtrace:debugtrace:2.+'
}
```

### 7. ライセンス

[MIT ライセンス (MIT)](LICENSE.txt)

*&copy; 2015 Masato Kokubo*

### 8. リンク

[API 仕様](http://masatokokubo.github.io/DebugTrace-java/javadoc_ja/index.html)

[English](README.md)
