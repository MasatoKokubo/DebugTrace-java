* Added `LogOptions` class.
* Added the following methods to `DebugTrace` class.
    * `public static boolean print(String name, boolean value, LogOptions logOptions)`
    * `public static char print(String name, char value, LogOptions logOptions)`
    * `public static byte print(String name, byte value, LogOptions logOptions)`
    * `public static short print(String name, short value, LogOptions logOptions)`
    * `public static int print(String name, int value, LogOptions logOptions)`
    * `public static long print(String name, long value, LogOptions logOptions)`
    * `public static float print(String name, float value, LogOptions logOptions)`
    * `public static double print(String name, double value, LogOptions logOptions)`
    * `public static <T> T print(String name, T value, LogOptions logOptions)`
    * `public static boolean print(String name, BooleanSupplier valueSupplier, LogOptions logOptions)`
    * `public static int print(String name, IntSupplier valueSupplier, LogOptions logOptions)`
    * `public static long print(String name, LongSupplier valueSupplier, LogOptions logOptions)`
    * `public static double print(String name, DoubleSupplier valueSupplier, LogOptions logOptions)`
    * `public static <T> T print(String name, Supplier<T> valueSupplier, LogOptions logOptions)`

* Changed default values for the following properties.

|Property Name|New Default Value|Old Default Value|
|:------------|:---------------:|:---------------:|
|`minimumOutputSize`  |`Integer.MAX_VALUE`|5|
|`minimumOutputLength`|`Integer.MAX_VALUE`|5|

---
*Japanese*

* `LogOptions`クラスを追加しました。
* `DebugTrace`クラスに以下のメソッドを追加しました。
    * `public static boolean print(String name, boolean value, LogOptions logOptions)`
    * `public static char print(String name, char value, LogOptions logOptions)`
    * `public static byte print(String name, byte value, LogOptions logOptions)`
    * `public static short print(String name, short value, LogOptions logOptions)`
    * `public static int print(String name, int value, LogOptions logOptions)`
    * `public static long print(String name, long value, LogOptions logOptions)`
    * `public static float print(String name, float value, LogOptions logOptions)`
    * `public static double print(String name, double value, LogOptions logOptions)`
    * `public static <T> T print(String name, T value, LogOptions logOptions)`
    * `public static boolean print(String name, BooleanSupplier valueSupplier, LogOptions logOptions)`
    * `public static int print(String name, IntSupplier valueSupplier, LogOptions logOptions)`
    * `public static long print(String name, LongSupplier valueSupplier, LogOptions logOptions)`
    * `public static double print(String name, DoubleSupplier valueSupplier, LogOptions logOptions)`
    * `public static <T> T print(String name, Supplier<T> valueSupplier, LogOptions logOptions)`

* 以下のプロパティのデフォルト値を変更しました。

|プロパティ名|新デフォルト値|旧デフォルト値|
|:---------|:----------:|:----------:|
|`minimumOutputSize`  |`Integer.MAX_VALUE`|5|
|`minimumOutputLength`|`Integer.MAX_VALUE`|5|
