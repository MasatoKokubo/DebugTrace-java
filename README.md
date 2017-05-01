DebugTrace-java
===========

DebugTrace-java is a library that outputs trace logs when debugging Java programs and is available in Java 8 or later.  
By embedding `DebugTrace.enter ()` and `DebugTrace.leave ()` at the start and end of methods, you can understand the execution situation of the Java program under development.

### 1. Features

- Automatically outputs caller's class name, method name, source file and line number.
- Automatically indents the log with nesting methods and objects.
- Automatically output logs when changing threads.
- Uses reflection to output the contents of classes that do not implement the `toString` method.
- You can customize the output content in DebugTrace.properties.
- There is no dependency library at run time. (Required if you use the following logging library)
- You can use the following logging library.
     - JDK Logger
     - Log 4 j
     - Log 4 j 2
     - SLF 4 J
     - Console (stdout and stderr)

### 2. How to use

Do the following on the method you are debugging.

1. Insert `DebugTrace.enter()` at the beginning of target methods.
1. Insert `DebugTrace.leave()` at the end of target methods (just before the return statement).
1. If the target method has arguments, insert `Debug.print(...)` to output its contents to the log.
1. If the target method has return statements, insert `Debug.print(...)` to output its contents to the log.

The following is an example of Java source used DebugTrace methods and the log of when it has been executed.

```java:Example1.java
// An example of Java Source
package example;

import java.lang.reflect.Array;
import org.debugtrace.DebugTrace;

public class Example1 {
  // main
  public static void main(String[] args) {
    DebugTrace.enter(); // added

    @SuppressWarnings("unused")
    Point[] points = newArray(Point.class, 2);

    DebugTrace.leave(); // added
  }

  // Creates a new array
  public static <E> E[] newArray(Class<E> elementType, int length) {
    DebugTrace.enter(); // added
    DebugTrace.print("elementType", elementType); // added
    DebugTrace.print("length", length); // added
    @SuppressWarnings("unchecked")
    E[] array = (E[])Array.newInstance(elementType, length);
    DebugTrace.print("1 array", array); // added
    try {
      for (int index = 0; index < length; ++index)
        array[index] = elementType.getConstructor().newInstance();
    }
    catch (RuntimeException e) {throw e;}
    catch (Exception e) {throw new RuntimeException(e);}
    DebugTrace.print("2 array", array); // added
    DebugTrace.leave(); // added
    return array;
  }

  // Point class
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
2017-04-30 16:16:35.930 DebugTrace 2.4.0 / logger: org.debugtrace.logger.Std$Out
2017-04-30 16:16:35.947 
2017-04-30 16:16:35.949 ______________________________ main ______________________________ <- Thread change log
2017-04-30 16:16:35.951 
2017-04-30 16:16:35.962 Enter example.Example1.main (Example1.java:15)
2017-04-30 16:16:35.982 | Enter example.Example1.newArray (Example1.java:25)
2017-04-30 16:16:35.989 | | elementType = (Class)class example.Example1$Point (Example1.java:26)
2017-04-30 16:16:35.990 | | length = 2 (Example1.java:27)
2017-04-30 16:16:35.992 | | 1 array = (example.Example1.Point[] length:2)[
2017-04-30 16:16:35.993 | |   null,
2017-04-30 16:16:35.995 | |   null,
2017-04-30 16:16:35.997 | | ] (Example1.java:30)
2017-04-30 16:16:36.000 | | 2 array = (example.Example1.Point[] length:2)[
2017-04-30 16:16:36.000 | |   (example.Example1.Point)[
2017-04-30 16:16:36.001 | |     x: 0,
2017-04-30 16:16:36.002 | |     y: 0,
2017-04-30 16:16:36.003 | |   ],
2017-04-30 16:16:36.004 | |   (example.Example1.Point)[
2017-04-30 16:16:36.004 | |     x: 0,
2017-04-30 16:16:36.006 | |     y: 0,
2017-04-30 16:16:36.006 | |   ],
2017-04-30 16:16:36.006 | | ] (Example1.java:37)
2017-04-30 16:16:36.008 | Leave example.Example1.newArray (Example1.java:38)
2017-04-30 16:16:36.010 Leave example.Example1.main (Example1.java:20)
```

### 3. Method List

This library has the following methods. These are all static methods of org.debugtrace.DebugTrace class with no return value.

|Method Name|Arguments|Outline of Processing|
|:--|:--|:--|
|enter|None|Output method start to log.|
|leave|None|Output method end to log.|
|print|`message`: a message|Output the message to log.|
|print|`messageSupplier`: a supplier of message|Get a message from the supplier and output it to log.|
|print|`name`: a name of value<br>`value`: a value|Output to the log in the form of<br>`<value name> = <value>`.<br>The type of value is `boolean`, `char`, `byte`, `short`, `int`, `long`, `float`, `double` or `Object`.|
|print|`name`: a name of the value<br>`valueSupplier`: a supplier of the value |Gets a value from the supplier and outputs to the log in the form of<br>`<value name> = <value>`<br>The valueSupplier type is `BooleanSupplier`, ` IntSupplier`, `LongSupplier` or ` Supplier<T>`.|
|print<br>*(v2.4.0~)*|`mapName`: the name of map to get constant name corresponding to number<br>`name`: a name of value<br>`value`: a value|Output to the log in the form of<br>`<value name> = <value>(<constant name>)`.<br>The type of value is `boolean`, `char`, `byte`, `short`, `int`, `long`, `float`, `double` or `Object`.|
|print<br>*(v2.4.0~)*|`mapName`: the name of map to get constant name corresponding to number<br>`name`: a name of the value<br>`valueSupplier`: a supplier of the value |Gets a value from the supplier and outputs to the log in the form of<br>`<value name> = <value>(<constant name>)`<br>The valueSupplier type is `BooleanSupplier`, ` IntSupplier`, `LongSupplier` or ` Supplier<T>`.|

### 4. Property List of *DebugTrace.properties* file

DebugTrace read `DebugTrace.properties` file in the classpath on startup.  
You can specify following properties in the `DebugTrace.properties` file.  

|Property Name|Value to be set|Default Value|
|:--|:--|:--|
|logger| Logger DebugTrace uses<br>`Jdk` : use JDK Logger<br>`Log4j` : use Log4j 1<br>`Log4j2` : use Log4j 2<br>`SLF4J` : use SLF4J<br>`Std$Out`: output to stdout<br>`Std$Err` : output to stderr|`Std$Out`|
|logLevel|Log level<br>`default` : the lowest level for each logger<br>`finest,` `finer`, `fine`, `config`, `info`, `warning`, `severe` : when use JDK Logger<br>`trace`, `debug`, `info`, `warn`, `error`, `fatal` : when use Log4j 1 or 2<br>`trace`, `debug`, `info`, `warn`, `error` : when use SLF4J<br>|`finest`: when use JDK Logger<br>`trace`: when use Log4j 1, 2 or SLF4J|
|enterString|The string used after enter method<br><br>parameters:<br>`%1`: The class name of the caller<br>`%2`: The method name of the caller<br>`%3`: The file name of the caller<br>`%4`: The line numnber of the caller|`Enter %1$s.%2$s (%3$s:%4$d)`|
|leaveString|The string used before leave method<br><br>parameters:<br>`%1`: The class name of the caller<br>`%2`: The method name of the caller<br>`%3`: The file name of the caller<br>`%4`: The line numnber of the caller|`Leave %1$s.%2$s (%3$s:%4$d)`|
|threadBoundaryString|The string output in the threads boundary.<br><br>parameter:<br>`%1`: The thread name|`______________________________ %1$s ______________________________`|
|classBoundaryString|The string output in the classes boundary.<br><br>parameter:<br>`%1`: The class name|`____ %1$s ____`|
|indentString|String of one code indent<br>`"\\s"` is change to a space character|`|\\s`|
|dataIndentString|String of one data indent|`\\s\\s`|
|limitString|The string to represent that it has exceeded the limit|`...`|
|nonPrintString<br>*(v1.5.0~)*|String of value in the case of properties that do not print the value|`***`|
|cyclicReferenceString|The string to represent that the cyclic reference occurs|`\\s* cyclic reference *\\s`|
|varNameValueSeparator|Separator between the variable name and value|`\\s=\\s`|
|keyValueSeparator|Separator between the key and value for Map object|`:\\s`|
|fieldNameValueSeparator|Separator between the field name and value|`:\\s`|
|printSuffixFormat|Output format of print method suffix<br><br>parameters:<br>`%1`: The class name of the caller<br>`%2`: The method name of the caller<br>`%3`: The file name of the caller<br>`%4`: The line numnber of the caller|`\\s(%3$s:%4$d)`|
|indexFormat|Output format of index of array and Collection<br><br>parameter:<br>`%1`: The index|`%1$d:\\s`|
|utilDateFormat|Output format of java.util.Date<br><br>parameter:<br>`%1`: a java.util.Date<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS`|
|sqlDateFormat|Output format of java.sql.Date<br><br>parameter:<br>`%1`: a java.sql.Date|`%1$tY-%1$tm-%1$td`|
|timeFormat|Output format of java.sql.Time<br><br>parameter:<br>`%1`: a java.sql.Time<br>|`%1$tH:%1$tM:%1$tS`|
|timestampFormat|Output format of java.sql.Timestamp<br><br>parameter:<br>`%1`: a java.sql.Timestamp<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL`|
|arrayLimit|Limit of array and Collection elements to output|512|
|byteArrayLimit|Limit of byte array (byte[]) elements to output|8192|
|mapLimit|Limit of Map elements to output|512|
|stringLimit|Limit of String characters to output|8192|
|outputIndexLength|Length of array and Collection to output index|9|
|nonPrintProperties<br>*(v2.2.0~)*|Properties not to be output<br>value<br><br>format:<br>`<full class name>#<property name>,`<br>`<full class name>#<property name>,`<br>`...`|*\<empty list\>*|
|defaultPackage<br>*(v2.3.0~)*|Default package of your java source|*\<none\>*|
|defaultPackageString<br>*(v2.3.0~)*|String replacing the default package part|`...`|
|reflectionClasses<br>*(v2.4.0~)*|Classe names that output content by reflection even if toString method is implemented|*\<empty list\>*|
|mapNameMap<br>*(v2.4.0~)*|Map for obtaining map name corresponding to variable name<br><br>value format:<br>`<variable name>: <map name>,`<br>`<variable name>: <map name>,`<br>`...`|*\<empty map\>*|
|\<map name\><br>*(v2.4.0~)*|Map of numbers (as key) and constant names (as value) corresponding to the numbers<br><br>value format:<br>`<number>:<constant name>,`<br>`<number>:<constant name>,`<br>`...`|The following map names are defined.<br>`Calendar`: Calendar.ERA etc.<br>`CalendarWeek`: Calendar.SUNDAY etc.<br>`CalendarMonth`: Calendar.JANUARY etc.<br>`CalendarAmPm`: Calendar.AM etc.<br>`SqlTypes`: java.sql.Types.BIT etc.|

#### 4.1. nonPrintProperties, nonPrintString

DebugTrace use reflection to output object contents if the `toString` method is not implemented.
If there are other object references, the contents of objects are also output.
However, if there is circular reference, it will automatically detect and suspend output.
You can suppress output by specifying the `nonPrintProperties` property and
can specify multiple values of this property separated by commas.  
The value of the property specified by `nonPrintProperties` are output as the string specified by` nonPrintString` (default: `***`).

```properties:DebugTrace.properties
# Example of nonPrintProperties
nonPrintProperties = \
    org.lightsleep.helper.EntityInfo#columnInfos,\
    org.lightsleep.helper.EntityInfo#keyColumnInfos,\
    org.lightsleep.helper.ColumnInfo#entityInfo
```

#### 4.2. Constant map and mapNameMap

The constant map is a map whose key is numeric and whose value is a constant name.
When you call the `print` method with the key (map name) of this property as an argument, the constant name is output with numerical value.

```properties:DebugTrace.properties
# Example of constant map
AppleBrand = \
    0: Apple.NO_BRAND,\
    1: Apple.AKANE,\
    2: Apple.AKIYO,\
    3: Apple.AZUSA,\
    4: Apple.YUKARI
```

```java
// Java source example
int appleBland = Apple.AZUSA;
DebugTrace.print("AppleBland", "appleBland", appleBland);
```

If you specify the map name corresponding to the variable name with the mapNameMap property, even if you do not specify the map name, the constant name is output.

```properties:DebugTrace.properties
# mapNameMap の例
mapNameMap = appleBland:AppleBrand
```

```java
// Java source example
int appleBland = Apple.YUKARI;
DebugTrace.print("appleBland", appleBland);
```

### 5. Examples of using logging libraries

The logger name of DebugTrace is `org.debugtrace.DebugTrace`.   

#### 5.1. Example of `logging.properties` (Jdk)

```properties:logging.properties
# logging.properties
handlers = java.util.logging.ConsoleHandler
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
java.util.logging.SimpleFormatter.format = [Jdk] %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %5$s%n
java.util.logging.ConsoleHandler.level = FINEST
org.debugtrace.DebugTrace.level = FINEST
```
*`-Djava.util.logging.config.file=<path>/logging.properties` is required as Java startup option*
#### 5.2. Example of `log4j.xml` (Log4j)

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

#### 5.3. Example of `log4j2.xml` (Log4j2)

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

#### 5.4. Example of `logback.xml` (SLF4J / Logback)

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

#### 6. Example of `build.gradle` when building with Gradle

```gradle:build.gradle
// build.gradle
repositories {
    jcenter()
}

dependencies {
    compile 'org.debugtrace:debugtrace:2.+'
}
```

#### 7. License

[The MIT License (MIT)](LICENSE.txt)

*&copy; 2015 Masato Kokubo*

[Japanese](README_ja.md)
