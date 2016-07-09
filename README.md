DebugTrace
===========

DebugTrace can be used in Java 8 and later.

The following are a java sample source used DebugTrace methods and a log of when the java sample was executed.  
Log bodys are automatically indent.

#### Sample source:

	package sample;

	import org.debugtrace.DebugTrace;

	// Sample2
	public class Sample2 {
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


#### Log of when the java sample has been executed:

	2016-07-09 19:00:10.146 DebugTrace 2.1.0 / logger: org.debugtrace.logger.Std$Out
	2016-07-09 19:00:10.171 
	2016-07-09 19:00:10.172 ______________________________ main ______________________________
	2016-07-09 19:00:10.173 
	2016-07-09 19:00:10.176 sample.Sample2.main (Sample2.java:13)
	2016-07-09 19:00:10.181 | args = (String[] length:3)[
	2016-07-09 19:00:10.182 |   "aaa",
	2016-07-09 19:00:10.184 |   "bbb",
	2016-07-09 19:00:10.184 |   "ccc",
	2016-07-09 19:00:10.185 | ] (Sample2.java:14)
	2016-07-09 19:00:10.185 | sample.Sample2.sub (Sample2.java:23)
	2016-07-09 19:00:10.188 | | args = (String[] length:3)[
	2016-07-09 19:00:10.189 | |   "aaa",
	2016-07-09 19:00:10.190 | |   "bbb",
	2016-07-09 19:00:10.190 | |   "ccc",
	2016-07-09 19:00:10.193 | | ] (Sample2.java:25)
	2016-07-09 19:00:10.194 | sample.Sample2.sub (Sample2.java:27)
	2016-07-09 19:00:10.194 sample.Sample2.main (Sample2.java:18)

DebugTrace read `DebugTrace.properties` file in the classpath on startup.  
You can specify following properties in the `DebugTrace.properties` file.  

#### Properties of `DebugTrace.properties` file:

|Name|Value|Default|
|:--|:--|:--|
|`logger`| Logger DebugTrace uses<br>Select from follows.<br>`Jdk` : When you use **JDK Logger**<br>`Log4j` : When you use **Log4J 1**<br>`Log4j2` : When you use **Log4J 2**<br>`SLF4J` : When you use **SLF4J**<br>`Std$Out`: When you output the **standard output**<br>`Std$Err` : When you output the **standard error output**<br>|`Std$Out`|
|`logLevel`|Log level DebugTrace outputs<br>Select from follows.`default` : The lowest level for each logger**<br>`finest,` `finer`, `fine`, `config`, `info`, `warning`, `severe` : **JDK Logger**<br>`trace`, `debug`, `info`, `warn`, `error`, `fatal` : **Log4J 1 or 2**<br>`trace`, `debug`, `info`, `warn`, `error` : **SLF4J**<br>|`finest`: JDK Logger<br>`trace`: Log4J 1, 2 or SLF4J|
|`enterString`|String used after enter method<br>**parameters:**<br>`%1`: The **class name** of the caller<br>`%2`: The **method name** of the caller<br>`%3`: The **file name** of the caller<br>`%4`: The **line numnber** of the call point|`%1$s.%2$s (%3$s:%4$d)`|
|`leaveString`|String used before leave method**parameters:**<br>`%1`: The **class name** of the caller<br>`%2`: The **method name** of the caller<br>`%3`: The **file name** of the caller<br>`%4`: The **line numnber** of the call point|`%1$s.%2$s (%3$s:%4$d)`|
|`threadBoundaryString`|String that is output in the threads boundary.<br>**parameter:**<br>`%1`: The **thread name**|`______________________________ %1$s ______________________________`|
|`classBoundaryString`|String that is output in the classes boundary.<br>**parameter:**<br>`%1`: The **class name**|`____ %1$s ____`|
|`indentString`|String of one code indent<br>`"\s"` is change to a space character|`|\s`|
|`dataIndentString`|String of one data indent|`\s\s`|
|`limitString`|String to represent that it has exceeded the limit|`...`|
|`nonPrintString`|String of value in the case of properties that do not print the value (@since 1.5.0)|`***`|
|`cyclicReferenceString`|String to represent that the cyclic reference occurs|`\s*** cyclic reference ***\s`|
|`varNameValueSeparator`|Separator between the variable name and value|`\s=\s`|
|`keyValueSeparator`|Separator between the key and value for Map object|`:\s`|
|`fieldNameValueSeparator`|Separator between the field name and value|`:\s`|
|`printSuffixFormat`|Output format of print method suffix**parameters:**<br>`%1`: The **class name** of the caller<br>`%2`: The **method name** of the caller<br>`%3`: The **file name** of the caller<br>`%4`: The **line numnber** of the call point|`\s(%3$s:%4$d)`|
|`indexFormat`|Output format of index of array and Collection<br>**parameter:**<br>`%1`: The **index**|`%1$d:\s`|
|`utilDateFormat`|Output format of **java.util.Date**<br>**parameter:**<br>`%1`: a **java.util.Date**<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS`|
|`sqlDateFormat`|Output format of **java.sql.Date**<br>**parameter:**<br>`%1`: a **java.sql.Date**|`%1$tY-%1$tm-%1$td`|
|`timeFormat`|Output format of **java.sql.Time**<br>**parameter:**<br>`%1`: a **java.sql.Time**<br>|`%1$tH:%1$tM:%1$tS`|
|`timestampFormat`|Output format of **java.sql.Timestamp**<br>**parameter:**<br>`%1`: a **java.sql.Timestamp**<br>|`%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL`|
|`arrayLimit`|Limit of **array** and **Collection** elements to output|512|
|`byteArrayLimit`|Limit of **byte array (byte[])** elements to output|8192|
|`mapLimit`|Limit of **Map** elements to output|512|
|`stringLimit`|Limit of **String** characters to output|8192|
|`outputIndexLength`|Length of array and Collection to output index|9|

The logger name of DebugTrace is `org.debugtrace.DebugTrace`.   

#### License

The MIT License (MIT)

*&copy; 2015 Masato Kokubo*
