DebugTrace-java
===========

DebugTrace-java は、デバッグ用のログを出力するためのライブラリで、Java 8 で利用できます。

以下は、DebugTrace のメソッドを使用した Java の例とそれを実行した際のログです。
ログは自動的にインデントされます。

#### DebugTrace-java の使用例

```java:Example2.java
package example;

import org.debugtrace.DebugTrace;

public class Example2 {
  // main
  public static void main(String[] args) {
    DebugTrace.enter();
    DebugTrace.print("args", args);

    sub(args);

    DebugTrace.leave();
  }

  // sub
  private static void sub(String[] args) {
    DebugTrace.enter();

    DebugTrace.print("args", args);

    DebugTrace.leave();
  }
}
```

#### 例を実行した際のログ

```log:debugtrace.log
2016-11-13 12:44:19.416 DebugTrace 2.3.0 / logger: org.debugtrace.logger.Std$Out
2016-11-13 12:44:19.449 
2016-11-13 12:44:19.450 ______________________________ main ______________________________
2016-11-13 12:44:19.451 
2016-11-13 12:44:19.456 Enter example.Example2.main (Example2.java:12)
2016-11-13 12:44:19.460 | args = (String[] length:3)[
2016-11-13 12:44:19.464 |   "aaa",
2016-11-13 12:44:19.466 |   "bbb",
2016-11-13 12:44:19.467 |   "ccc",
2016-11-13 12:44:19.468 | ] (Example2.java:13)
2016-11-13 12:44:19.470 | Enter example.Example2.sub (Example2.java:22)
2016-11-13 12:44:19.471 | | args = (String[] length:3)[
2016-11-13 12:44:19.471 | |   "aaa",
2016-11-13 12:44:19.472 | |   "bbb",
2016-11-13 12:44:19.472 | |   "ccc",
2016-11-13 12:44:19.474 | | ] (Example2.java:24)
2016-11-13 12:44:19.475 | Leave example.Example2.sub (Example2.java:26)
2016-11-13 12:44:19.476 Leave example.Example2.main (Example2.java:17)
```

DebugTrace は、クラスパスにある `DebugTrace.properties` ファイルを起動時に読み込みます。
`DebugTrace.properties` ファイルには以下のプロパティを指定できます。

#### *DebugTrace.properties* ファイルのプロパティ一覧

|プロパティ名|設定する値|デフォルト値|
|:--|:--|:--|
|`logger`| DebugTrace が使用するロガー<br><br>`Jdk` : **JDK ロガー** を使用<br><br>`Log4j` : **Log4j 1** を使用<br><br>`Log4j2` : **Log4j 2** を使用<br><br>`SLF4J` : **SLF4J** を使用<br><br>`Std$Out`: **stdout** へ出力<br><br>`Std$Err` : **stderr** へ出力|`Std$Out`|
|`logLevel`|Log level<br><br>`default` : 各ロガーでの最低レベル<br><br>`finest,` `finer`, `fine`, `config`, `info`, `warning`, `severe` : **JDK Logger** 使用時<br><br>`trace`, `debug`, `info`, `warn`, `error`, `fatal` : **Log4j 1 か 2** 使用時<br><br>`trace`, `debug`, `info`, `warn`, `error` : **SLF4J** 使用時<br>|`finest`: JDK Logger 使用時<br>`trace`: Log4j 1, 2 or SLF4J 使用時|
|`enterString`|メソッドに入った後の文字列<br><br>**パラメータ:**<br>`%1`: 呼出側の**クラス名**<br>`%2`: 呼出側の**メソッド名**<br>`%3`: 呼出側の**ファイル名**<br>`%4`: 呼出側の**行番号**|`Enter %1$s.%2$s (%3$s:%4$d)`|
|`leaveString`|メソッドから出る前の文字列<br><br>**パラメータ:**<br>`%1`: 呼出側の**クラス名**<br>`%2`: 呼出側の**メソッド名**<br>`%3`: 呼出側の**ファイル名**<br>`%4`: 呼出側の**行番号**|`Leave %1$s.%2$s (%3$s:%4$d)`|
|`threadBoundaryString`|スレッド境界で出力される文字列<br><br>**パラメータ:**<br>`%1`: **スレッド名**|`______________________________ %1$s ______________________________`|
|`classBoundaryString`|クラス境界で出力される文字列<br><br>**パラメータ:**<br>`%1`: **クラス名**|`____ %1$s ____`|
|`indentString`|コードのインデント文字列<br>`"\\s"` は空白文字1つに置き換える|`|\\s`|
|`dataIndentString`|データのインデント文字列|`\\s\\s`|
|`limitString`|制限を超えた場合に出力される文字列|`...`|
|`nonPrintString`<br>*(v1.5.0~)*|出力しないプロパティ値の文字列|`***`|
|`cyclicReferenceString`|循環参照が起きた場合に出力される文字列|`\\s*** cyclic reference ***\\s`|
|`varNameValueSeparator`|変数名と値のセパレータ|`\\s=\\s`|
|`keyValueSeparator`|マップでのキーと値のセパレータ|`:\\s`|
|`fieldNameValueSeparator`|フィールド名と値のセパレータ|`:\\s`|
|`printSuffixFormat`|print メソッドで付加される文字列のフォーマット<br><br>**パラメータ:**<br>`%1`: 呼出側の**クラス名**<br>`%2`: 呼出側の**メソッド名**<br>`%3`: 呼出側の**ファイル名**<br>`%4`: 呼出側の**行番号**|`\\s(%3$s:%4$d)`|
|`indexFormat`|配列とコレクションでのインデックスの出力フォーマット<br><br>**パラメータ:**<br>`%1`: The **index**|`%1$d:\\s`|
|`utilDateFormat`|**java.util.Date** の出力フォーマット<br><br>**パラメータ:**<br>`%1`: **java.util.Date** オブジェクト<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS`|
|`sqlDateFormat`|**java.sql.Date** の出力フォーマット<br><br>**パラメータ:**<br>`%1`: **java.sql.Date** オブジェクト|`%1$tY-%1$tm-%1$td`|
|`timeFormat`|**java.sql.Time** の出力フォーマット<br><br>**パラメータ:**<br>`%1`: **java.sql.Time** オブジェクト<br>|`%1$tH:%1$tM:%1$tS`|
|`timestampFormat`|Output format of **java.sql.Timestamp**<br><br>**パラメータ:**<br>`%1`: **java.sql.Timestamp** オブジェクト<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL`|
|`arrayLimit`|**配列**と**Collection** 要素の出力数の制限|512|
|`byteArrayLimit`|**バイト配列 (byte[])** 要素の出力数の制限|8192|
|`mapLimit`|**Map** 要素の出力制限数|512|
|`stringLimit`|**String** の出力文字数の制限|8192|
|`outputIndexLength`|**配列**と **Collection** のインデックスを出力する長さ|9|
|`nonPrintProperties.0`<br>`nonPrintProperties.1`<br>    `...`<br>*(v2.2.0~)*|出力しないプロパティの指定<br><br>**値のフォーマット:**<br> *\<フルクラス名\>#\<プロパティ名\>*|*\<なし\>*|
|`defaultPackage`<br>*(v2.3.0~)*|使用する Java ソースのデフォルトパッケージ|*\<なし\>*|
|`defaultPackageString`<br>*(v2.3.0~)*|デフォルトパッケージ部を置き換える文字列|`...`|

DebugTrace のロガー名は、`org.debugtrace.DebugTrace` です.   

#### `logging.properties` (JDK標準) の例

```properties:logging.properties
handlers = java.util.logging.ConsoleHandler
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
java.util.logging.SimpleFormatter.format = [Jdk] %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %5$s%n
java.util.logging.ConsoleHandler.level = FINEST
org.debugtrace.DebugTrace.level = FINEST
```
*Java 起動時オプションとして `-Djava.util.logging.config.file=<パス>/logging.properties` が必要*

#### `log4j.xml` (Log4j) の例

```xml:log4j.xml
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

#### `log4j2.xml` (Log4j 2) の例

```xml:log4j2.xml
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

#### `logback.xml` (SLF4J / Logback) の例

```xml:logback.xml
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

#### `build.gradle` の例

```gradle:build.gradle
repositories {
    jcenter()
}

dependencies {
    compile 'org.debugtrace:debugtrace:2.+'
}
```

#### ライセンス

[MIT ライセンス (MIT)](LICENSE.txt)

*&copy; 2015 Masato Kokubo*

[English](README.md)
