DebugTrace-java
===========

DebugTrace-java ia a library to output logs for debugging. It can be used in Java 8.

The following is an example of Java source used DebugTrace methods and the log of when it has been executed.
The log body is automatically indent.

#### Example used DebugTrace-java

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

#### The log of when the example has been executed

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

DebugTrace read `DebugTrace.properties` file in the classpath on startup.  
You can specify following properties in the `DebugTrace.properties` file.  

#### Properties of `DebugTrace.properties` file

|Property Name|Value to be set|Default Value|
|:--|:--|:--|
|`logger`| Logger DebugTrace uses<br><br>`Jdk` : use **JDK Logger**<br><br>`Log4j` : use **Log4j 1**<br><br>`Log4j2` : use **Log4j 2**<br><br>`SLF4J` : use **SLF4J**<br><br>`Std$Out`: output to **stdout**<br><br>`Std$Err` : output to **stderr**|`Std$Out`|
|`logLevel`|Log level<br><br>`default` : the lowest level for each logger<br><br>`finest,` `finer`, `fine`, `config`, `info`, `warning`, `severe` : when use **JDK Logger**<br><br>`trace`, `debug`, `info`, `warn`, `error`, `fatal` : when use **Log4j 1 or 2**<br><br>`trace`, `debug`, `info`, `warn`, `error` : when use **SLF4J**<br>|`finest`: when use JDK Logger<br>`trace`: when use Log4j 1, 2 or SLF4J|
|`enterString`|The string used after enter method<br><br>**parameters:**<br>`%1`: The **class name** of the caller<br>`%2`: The **method name** of the caller<br>`%3`: The **file name** of the caller<br>`%4`: The **line numnber** of the caller|`Enter %1$s.%2$s (%3$s:%4$d)`|
|`leaveString`|The string used before leave method<br><br>**parameters:**<br>`%1`: The **class name** of the caller<br>`%2`: The **method name** of the caller<br>`%3`: The **file name** of the caller<br>`%4`: The **line numnber** of the caller|`Leave %1$s.%2$s (%3$s:%4$d)`|
|`threadBoundaryString`|The string output in the threads boundary.<br><br>**parameter:**<br>`%1`: The **thread name**|`______________________________ %1$s ______________________________`|
|`classBoundaryString`|The string output in the classes boundary.<br><br>**parameter:**<br>`%1`: The **class name**|`____ %1$s ____`|
|`indentString`|String of one code indent<br>`"\\s"` is change to a space character|`|\\s`|
|`dataIndentString`|String of one data indent|`\\s\\s`|
|`limitString`|The string to represent that it has exceeded the limit|`...`|
|`nonPrintString`<br>*(v1.5.0~)*|String of value in the case of properties that do not print the value|`***`|
|`cyclicReferenceString`|The string to represent that the cyclic reference occurs|`\\s*** cyclic reference ***\\s`|
|`varNameValueSeparator`|Separator between the variable name and value|`\\s=\\s`|
|`keyValueSeparator`|Separator between the key and value for Map object|`:\\s`|
|`fieldNameValueSeparator`|Separator between the field name and value|`:\\s`|
|`printSuffixFormat`|Output format of print method suffix<br><br>**parameters:**<br>`%1`: The **class name** of the caller<br>`%2`: The **method name** of the caller<br>`%3`: The **file name** of the caller<br>`%4`: The **line numnber** of the caller|`\\s(%3$s:%4$d)`|
|`indexFormat`|Output format of index of array and Collection<br><br>**parameter:**<br>`%1`: The **index**|`%1$d:\\s`|
|`utilDateFormat`|Output format of **java.util.Date**<br><br>**parameter:**<br>`%1`: a **java.util.Date**<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS`|
|`sqlDateFormat`|Output format of **java.sql.Date**<br><br>**parameter:**<br>`%1`: a **java.sql.Date**|`%1$tY-%1$tm-%1$td`|
|`timeFormat`|Output format of **java.sql.Time**<br><br>**parameter:**<br>`%1`: a **java.sql.Time**<br>|`%1$tH:%1$tM:%1$tS`|
|`timestampFormat`|Output format of **java.sql.Timestamp**<br><br>**parameter:**<br>`%1`: a **java.sql.Timestamp**<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL`|
|`arrayLimit`|Limit of **array** and **Collection** elements to output|512|
|`byteArrayLimit`|Limit of **byte array (byte[])** elements to output|8192|
|`mapLimit`|Limit of **Map** elements to output|512|
|`stringLimit`|Limit of **String** characters to output|8192|
|`outputIndexLength`|Length of **array** and **Collection** to output index|9|
|`nonPrintProperties.0`<br>`nonPrintProperties.1`<br>    `...`<br>*(v2.2.0~)*|Non print properties<br><br>**value format:**<br> *\<full class name\>#\<property name\>*|*\<none\>*|
|`defaultPackage`<br>*(v2.3.0~)*|Default package of your java source|*\<none\>*|
|`defaultPackageString`<br>*(v2.3.0~)*|String replacing the default package part|`...`|

The logger name of DebugTrace is `org.debugtrace.DebugTrace`.   

#### Example of `logging.properties` (Jdk)

```properties:logging.properties
handlers = java.util.logging.ConsoleHandler
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
java.util.logging.SimpleFormatter.format = [Jdk] %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %5$s%n
java.util.logging.ConsoleHandler.level = FINEST
org.debugtrace.DebugTrace.level = FINEST
```
*`-Djava.util.logging.config.file=<path>/logging.properties` is required as Java startup option*
#### Example of `log4j.xml` (Log4j)

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

#### Example of `log4j2.xml` (Log4j2)

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

#### Example of `logback.xml` (SLF4J / Logback)

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

#### Example in `build.gradle`

```gradle:build.gradle
repositories {
    jcenter()
}

dependencies {
    compile 'org.debugtrace:debugtrace:2.+'
}
```

#### License

[The MIT License (MIT)](LICENSE.txt)

*&copy; 2015 Masato Kokubo*

[Japanese](README_ja.md)
