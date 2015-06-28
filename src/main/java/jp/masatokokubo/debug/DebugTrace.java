/*
	DebugTrace.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/
package jp.masatokokubo.debug;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
	A utility class for debugging.<br>
	Call DebugTrace.enter and DebugTrace.leave methods when enter and leave your methods,
	then outputs execution trace of the program.
	@since 1.0.0
	@author Masato Kokubo
*/
public class DebugTrace {
	private static class State {
		public int nestLevel       = 0; // Nest Level
		public int beforeNestLevel = 0; // Before Nest Level
	}

	// Map for wrppaer classes of primitive type to primitive type
	private static final Map<Class<?>, Class<?>> primitiveTypeMap = new HashMap<>();
	static {
		primitiveTypeMap.put(Boolean  .class, boolean.class);
		primitiveTypeMap.put(Character.class, char   .class);
		primitiveTypeMap.put(Byte     .class, byte   .class);
		primitiveTypeMap.put(Short    .class, short  .class);
		primitiveTypeMap.put(Integer  .class, int    .class);
		primitiveTypeMap.put(Long     .class, long   .class);
		primitiveTypeMap.put(Float    .class, float  .class);
		primitiveTypeMap.put(Double   .class, double .class);
	}

	// Map of classes that dose not output the name
	private static final Map<Class<?>, Boolean> noOutputTypeMap = new HashMap<>();
	static {
		noOutputTypeMap.put(boolean  .class, Boolean.TRUE);
		noOutputTypeMap.put(char     .class, Boolean.TRUE);
		noOutputTypeMap.put(int      .class, Boolean.TRUE);
		noOutputTypeMap.put(String   .class, Boolean.TRUE);
		noOutputTypeMap.put(Date     .class, Boolean.TRUE);
		noOutputTypeMap.put(Time     .class, Boolean.TRUE);
		noOutputTypeMap.put(Timestamp.class, Boolean.TRUE);
	}

	// Map of component types that dose not output the name
	private static final Map<Class<?>, Boolean> noOutputComponentTypeMap = new HashMap<>();
	static {
		noOutputComponentTypeMap.put(boolean  .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(char     .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(byte     .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(short    .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(int      .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(long     .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(float    .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(double   .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(String   .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(Date     .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(Time     .class, Boolean.TRUE);
		noOutputComponentTypeMap.put(Timestamp.class, Boolean.TRUE);
	}

	// Prefixes of getter methods
	private static final String[] getterPrefixes = {"", "get", "is"};

	// Resources
	private static final Resource resource = new Resource(DebugTrace.class,
		string -> {
			if (string != null) {
				StringBuilder buff = new StringBuilder(string.length());
				boolean escape = false;
				for (int index = 0; index < string.length(); ++index) {
					char ch = string.charAt(index);
					if (escape) {
						if      (ch == 't' ) buff.append('\t'); // 09 HT
						else if (ch == 'n' ) buff.append('\n'); // 0A LF
						else if (ch == 'r' ) buff.append('\r'); // 0D CR
						else if (ch == 's' ) buff.append(' ' ); // 20 SPACE
						else if (ch == '\\') buff.append('\\');
						else                 buff.append(ch);
						escape = false;
					} else {
						if (ch == '\\')
							escape = true;
						else
							buff.append(ch);
					}
				}
				string = buff.toString();
			}
			return string;
		}
	);

	private static final String threadChangeString    = resource.getString("threadChangeString"   ); // String to output when a thread has been changed
	private static final String indentString          = resource.getString("indentString"         ); // String of indent
	private static final String enterString           = resource.getString("enterString"          ); // String at enter
	private static final String leaveString           = resource.getString("leaveString"          ); // String at leave
	private static final String resultSetRowSeparator = resource.getString("resultSetRowSeparator"); // Line separator when output a ResultSet object
	private static final String utilDateFormat        = resource.getString("utilDateFormat"       ); // Format when output a java.util.Date object
	private static final String sqlDateFormat         = resource.getString("sqlDateFormat"        ); // Format when output a java.sql.Date object
	private static final String timeFormat            = resource.getString("timeFormat"           ); // Format when output a Time object
	private static final String timestampFormat       = resource.getString("timestampFormat"      ); // Format when output a Timestamp object
	private static final int    arrayMax              = resource.getInt   ("arrayMax"             ); // Max length when output an array
	private static final int    byteArrayMax          = resource.getInt   ("byteArrayMax"         ); // Max length when output a byte array
	private static final int    mapMax                = resource.getInt   ("mapMax"               ); // Max count when output a map
	private static final int    stringMax             = resource.getInt   ("stringMax"            ); // Max length when output a string
	private static final int    resultSetMaxRows      = resource.getInt   ("resultSetMaxRows"     ); // Max rows when output a ResultSet object
	private static final int    resultSetMaxColumns   = resource.getInt   ("resultSetMaxColumns"  ); // Max columns when output a ResultSet object

	// Append timestamp
	private static String appendTimestamp(String string) {
		return String.format(timestampFormat, new Timestamp(System.currentTimeMillis())) + " " + string;
	}

	// Logger map
	private static final Map<String, Logger> loggerMap = new LinkedHashMap<>();
	static {
		loggerMap.put("System.out", messgae -> System.out.println(appendTimestamp(messgae)));
		loggerMap.put("System.err", messgae -> System.err.println(appendTimestamp(messgae)));
	}

	// Logger
	private static Logger logger = null;

	static {
		String loggerName = null;
		try {
			loggerName = resource.getString("logger");
			logger = loggerMap.get(loggerName);

			if (logger == null) {
				if (loggerName.indexOf('.') == -1)
					loggerName = Logger.class.getPackage().getName() + '.' + loggerName;
				logger = (Logger)Class.forName(loggerName).newInstance();
			}
		}
		catch (Exception e) {
			if (loggerName != null)
				System.err.println("DebugTrace:" + e.toString() + "(" + loggerName + ")");
		}

		if (logger == null)
			logger = loggerMap.entrySet().iterator().next().getValue();
	}

	// Whether tracing is enabled
	private static final boolean enabled = logger.isTraceEnabled();

	// Array of indent strings
	private static final String[] indentStrings = new String[64];
	static {
		indentStrings[0] = "";
		IntStream.iterate(1, index -> index + 1).limit(indentStrings.length - 1)
			.forEach(index -> indentStrings[index] = indentStrings[index - 1] + indentString);
	}

	// Map of thread id to the indent state
	private static final Map<Long,  State> stateMap = new HashMap<>();

	// Before thread id
	private static long beforeThreadId;

	static {
		println("DebugTrace 1.0.0 / logger wrapper", logger.getClass().getSimpleName());
		println("");
	}

	private DebugTrace() {}

	/**
		Returns indent state.
	*/
	private static State getState() {
		State state = null;
		Long threadId = Thread.currentThread().getId();

		if (stateMap.containsKey(threadId)) {
			state = stateMap.get(threadId);
		} else {
			state = new State();
			stateMap.put(threadId, state);
		}

		return state;
	}

	/**
		Returns whether tracing is enabled.
		@return true if tracing is enabled; false otherwise
	*/
	public static boolean isEnabled() {return enabled;}

	/**
		Returns a string corresponding to the current indent.
		@return A string corresponding to the current indent
	*/
	private static String getIndentString() {
		State state = getState();

		return indentStrings[
			state.nestLevel < 0 ? 0 :
			state.nestLevel >= indentStrings.length ? indentStrings.length - 1
				: state.nestLevel];
	}

	/**
		Initializes the nest level.
	*/
	public static void initNestLevel() {
		State state = getState();
		state.nestLevel       = 0;
		state.beforeNestLevel = 0;
	}

	/**
		Common start processing of output.
	*/
	private static void printlnStart() {
		Thread thread = Thread.currentThread();
		long threadId = thread.getId();
		if (threadId !=  beforeThreadId) {
			// Thread changing
			logger.trace(""); // Line break
			logger.trace(String.format(threadChangeString, thread.getName(), threadId));
			logger.trace(""); // Line break

			beforeThreadId = threadId;
		}
	}

	/**
		Common end processing of output.
	*/
	private static void printlnEnd() {
		beforeThreadId = Thread.currentThread().getId();
	}

	/**
		Call this method at entrance of your methods.
	*/
	public static void enter() {
		if (enabled) {
			synchronized(stateMap) {
				printlnStart(); // Common start processing of output

				State state = getState();
				if (state.beforeNestLevel > state.nestLevel)
					logger.trace(""); // Line break

				logger.trace(getIndentString() + getCallerInfo(enterString));

				state.beforeNestLevel = state.nestLevel;
				++state.nestLevel;
			}
		}
	}

	/**
		Call this method at exit of your methods.
	*/
	public static void leave() {
		if (enabled) {
			synchronized(stateMap) {
				printlnStart(); // Common start processing of output

				State state = getState();
				state.beforeNestLevel = state.nestLevel;
				--state.nestLevel;

				logger.trace(getIndentString() + getCallerInfo(leaveString));

				printlnEnd(); // Common end processing of output
			}
		}
	}

	/**
		Returns a string of the caller information.
	*/
	private static String getCallerInfo(String baseString) {
		StackTraceElement element = new Throwable().getStackTrace()[2]; // Element of before call of the stack traces
		return String.format(baseString,
			element.getClassName(), element.getMethodName(), element.getLineNumber());
	}

	/**
		Outputs the message to the log.
		@param message a message (accept null)
	*/
	public static void println(String message) {
		if (enabled) {
			synchronized(stateMap) {
				printlnStart(); // Common start processing of output

				logger.trace(getIndentString() + message);

				printlnEnd(); // Common end processing of output
			}
		}
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a value (accept null)
		@param isPrimitive if the value is primitive type then true
	*/
	private static void println(String name, Object value, boolean isPrimitive) {
		synchronized(stateMap) {
			printlnStart(); // Common start processing of output

			StringBuilder buff = new StringBuilder(getIndentString());
			buff.append(name).append(':').append(toString(value, isPrimitive, false));
			logger.trace(buff.toString());

			printlnEnd(); // Common end processing of output
		}
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a boolean value
	*/
	public static void println(String name, boolean value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a value
	*/
	public static void println(String name, char value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a value
	*/
	public static void println(String name, byte value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a value
	*/
	public static void println(String name, short value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a int value
	*/
	public static void println(String name, int value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a long value
	*/
	public static void println(String name, long value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a float value
	*/
	public static void println(String name, float value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a value
	*/
	public static void println(String name, double value) {
		if (enabled)
			println(name, value, true);
	}

	/**
		Outputs the name and value to the log.
		@param name a name (accept null)
		@param value a value (accept null)
	*/
	public static void println(String name, Object value) {
		if (enabled)
			println(name, value, false);
	}

	/**
		Outputs a message to the log.
		@param messageSupplier a message supplier (not accept null)
	*/
	public static void println(Supplier<String> messageSupplier) {
		if (enabled)
			println(messageSupplier.get());
	}

	/**
		Outputs a value to the log.
		@param <T> type of the value
		@param name a name (accept null)
		@param valueSupplier a value supplier (not accept null)
	*/
	public static <T> void println(String name, Supplier<T> valueSupplier) {
		if (enabled)
			println(name, valueSupplier.get(), false);
	}

	/**
		Outputs a boolean value to the log.
		@param name a name (accept null)
		@param valueSupplier a boolean value supplier (not accept null)
	*/
	public static void println(String name, BooleanSupplier valueSupplier) {
		if (enabled)
			println(name, valueSupplier.getAsBoolean(), true);
	}

	/**
		Outputs a int value to the log.
		@param name a name (accept null)
		@param valueSupplier an int value supplier (not accept null)
	*/
	public static void println(String name, IntSupplier valueSupplier) {
		if (enabled)
			println(name, valueSupplier.getAsInt(), true);
	}

	/**
		Outputs a long value to the log.
		@param name a name (accept null)
		@param valueSupplier the long supplier (not accept null)
	*/
	public static void println(String name, LongSupplier valueSupplier) {
		if (enabled)
			println(name, valueSupplier.getAsLong(), true);
	}

	/**
		Outputs a double value to the log.
		@param name a name (accept null)
		@param valueSupplier a double value supplier (not accept null)
	*/
	public static void println(String name, DoubleSupplier valueSupplier) {
		if (enabled)
			println(name, valueSupplier.getAsDouble(), true);
	}

	/**
		Returns a string representation of the value.
		@param value a value object
		@param isPrimitive if the value is primitive type then true, otherwise false
		@param isComponent if the value is component of an array, otherwise false
		@return a string representation of the value
	*/
	public static String toString(Object value, boolean isPrimitive, boolean isComponent) {
		String string = "null";

		if (value != null) {
			StringBuilder buff = new StringBuilder();

			Class<?> type = value.getClass();
			if (isPrimitive) {
				type = primitiveTypeMap.get(type);
				if (type == null)
					type = value.getClass();
			}

			String typeName = getTypeName(type, value, isComponent, 0);
			if (typeName != null)
				buff.append(typeName);

			if (type.isArray()) {
				// Array
				if (type == char[].class)
					// String Array
					append(buff, ((char[])value));
				else if (type == byte[].class)
					// Byte Array
					append(buff, ((byte[])value));
				else
					// Other Array
					appendArray(buff, value);

			} else if (value instanceof Boolean) {
				// Boolean
				buff.append(value);

			} else if (value instanceof Character) {
				// String
				buff.append('\'');
				append(buff, ((Character)value).charValue());
				buff.append('\'');

			} else if (value instanceof Number) {
				// Number
				if (value instanceof BigDecimal)
					// BigDecimal
					buff.append(((BigDecimal)value).toPlainString());
				else
					// Other Number
					buff.append(value);

			} else if (value instanceof java.util.Date) {
				// Date
				if (value instanceof Date)
					// sql Date
					buff.append(String.format(sqlDateFormat, value));

				else if (value instanceof Time)
					// Time
					buff.append(String.format(timeFormat, value));

				else if (value instanceof Timestamp)
					// Timestamp
					buff.append(String.format(timestampFormat, value));

				else
					// Other Date
					buff.append(String.format(utilDateFormat, value));

			} else if (value instanceof CharSequence) {
				// CharSequence
				append(buff, (CharSequence)value);

			} else if (value instanceof Iterable) {
				// Iterable
				append(buff, (Iterable<?>)value);

			} else if (value instanceof Map) {
				// Map
				append(buff, (Map<?,?>)value);

			} else if (value instanceof Clob) {
				// Clob
				try {
					long length = ((Clob)value).length();
					if (length > (long)stringMax)
						length = (long)(stringMax + 1);
					append(buff, ((Clob)value).getSubString(1L, (int)length));
				}
				catch (SQLException e) {
					buff.append(e);
				}

			} else if (value instanceof Blob) {
				// Blob
				try {
					long length = ((Blob)value).length();
					if (length > (long)byteArrayMax)
						length = (long)(byteArrayMax + 1);
					append(buff, ((Blob)value).getBytes(1L, (int)length));
				}
				catch (SQLException e) {
					buff.append(e);
				}

			} else if (value instanceof ResultSet) {
				// ResultSet
				append(buff, (ResultSet)value);

			} else {
				// Other
				if (hasToString(value))
					buff.append(value);
				else
					buff.append(toReflectString(value));
			}

			string = buff.toString();
		}

		return string;
	}

	/**
		Returns the type name to be output to the log.<br>
		If dose not output, returns null.
		@param type the type of the value
		@param value a value object
		@param isComponent if the value is component of an array, otherwise false
		@param nest current nest count
		@return the type name to be output to the log
	*/
	@SuppressWarnings("rawtypes")
	private static String getTypeName(Class<?>type, Object value, boolean isComponent, int nest) {
		String typeName = null;
		long length = -1L;
		int  size   = -1;

		if (type.isArray()) {
			// Array
			typeName = getTypeName(type.getComponentType(), null, false, nest + 1) + "[]";
			if (value != null)
				length = Array.getLength(value);
		} else {
			// Not Array
			if (nest > 0 || (isComponent ? !noOutputComponentTypeMap.containsKey(type) : !noOutputTypeMap.containsKey(type))) {
				// Output the type name
				typeName = type.getCanonicalName();
				if (typeName.startsWith("java.") && !typeName.equals("java.util.Date"))
					typeName = type.getSimpleName();

				if (value != null) {
					try {
						if      (value instanceof Blob      ) length = ((Blob      )value).length();
						else if (value instanceof Clob      ) length = ((Clob      )value).length();
						else if (value instanceof Collection) size   = ((Collection)value).size  ();
						else if (value instanceof Map       ) size   = ((Map       )value).size  ();
					}
					catch (SQLException e) {}
				}
			}
		}

		if (typeName != null) {
			if (length != -1L)
				typeName += " length:" + length;

			else if (size != -1)
				typeName += " size:" + size;

			if (nest == 0)
				typeName = "(" + typeName + ")";
		}

		return typeName;
	}

	/**
		Appends a character representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param ch a character
		@return the string buffer
	*/
	private static StringBuilder append(StringBuilder buff, char ch) {
		if (ch >= ' ' && ch != '\u007F') {
			if      (ch == '"' ) buff.append("\\\"");
			else if (ch == '\'') buff.append("\\'" );
			else if (ch == '\\') buff.append("\\\\");
			else                 buff.append(ch);
		} else {
			if      (ch == '\b') buff.append("\\b" ); // 07 BEL
			else if (ch == '\t') buff.append("\\t" ); // 09 HT
			else if (ch == '\n') buff.append("\\n" ); // 0A LF
			else if (ch == '\f') buff.append("\\f" ); // 0C FF
			else if (ch == '\r') buff.append("\\r" ); // 0D CR
			else buff.append("\\u").append(String.format("%04X", (short)ch));
		}

		return buff;
	}

	/**
		Appends a CharSequence representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param charSequence a CharSequence object (not accept null)
		@return the string buffer
	*/
	private static StringBuilder append(StringBuilder buff, CharSequence charSequence) {
		buff.append('"');
		for (int index = 0; index < charSequence.length(); ++index) {
			if (index >= stringMax) {
				buff.append("...");
				break;
			}
			append(buff, charSequence.charAt(index));
		}
		buff.append('"');

		return buff;
	}

	/**
		Appends a character array representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param chars a character array (not accept null)
		@return the string buffer
	*/
	private static StringBuilder append(StringBuilder buff, char[] chars) {
		buff.append('"');
		for (int index = 0; index < chars.length; ++index) {
			if (index >= stringMax) {
				buff.append("...");
				break;
			}
			append(buff, chars[index]);
		}
		buff.append('"');

		return buff;
	}

	/**
		Appends a byte array representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param bytes a byte array (not accept null)
		@return the string buffer
	*/
	private static StringBuilder append(StringBuilder buff, byte[] bytes) {
		buff.append('[');
		for (int index = 0; index < bytes.length; ++index) {
			if (index > 0) buff.append(", ");
			if (index >= byteArrayMax) {
				buff.append("...");
				break;
			}
			int value = bytes[index];
			if (value < 0) value += 256;
			char ch = (char)(value / 16 + '0');
			if (ch > '9') ch += 'A' - '9' - 1;
			buff.append(ch);
			ch = (char)(value % 16 + '0');
			if (ch > '9') ch += 'A' - '9' - 1;
			buff.append(ch);
		}
		buff.append(']');

		return buff;
	}

	/**
		Appends an object array representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param array an object array (not accept null)
		@return the string buffer
	*/
	private static StringBuilder appendArray(StringBuilder buff, Object array) {
		Class<?> componentType = array.getClass().getComponentType();

		int length = Array.getLength(array);

		buff.append('[');
		for (int index = 0; index < length; ++index) {
			if (index > 0) buff.append(", ");
			if (index >= arrayMax) {
				buff.append("...");
				break;
			}
			Object value = Array.get(array, index);

			buff.append(index).append(':').append(toString(value, componentType.isPrimitive(), true));
		}
		buff.append(']');

		return buff;
	}

	/**
		Appends an Iterable representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param iterable an Iterable object (not accept null)
		@return the string buffer
	*/
	private static StringBuilder append(StringBuilder buff, Iterable<?> iterable) {
		Iterator<?> iter = iterable.iterator();
		buff.append('[');
		for (int index = 0; iter.hasNext(); ++index) {
			if (index > 0) buff.append(", ");
			if (index >= arrayMax) {
				buff.append("...");
				break;
			}
			buff.append(index).append(':').append(toString(iter.next(), false, false));
		}
		buff.append(']');

		return buff;
	}

	/**
		Appends a Map representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param map a Map (not accept null)
		@return the string buffer
	*/
	private static <K,V> StringBuilder append(StringBuilder buff, Map<K,V> map) {
		Iterator<Map.Entry<K,V>> iter = map.entrySet().iterator();
		buff.append('[');
		for (int index = 0; iter.hasNext(); ++index) {
			if (index > 0) buff.append(", ");
			if (index >= mapMax) {
				buff.append("...");
				break;
			}
			Map.Entry<K,V> entry = iter.next();
			buff.append(toString(entry.getKey(), false, false))
				.append(':')
				.append(toString(entry.getValue(), false, false));
		}
		buff.append(']');

		return buff;
	}

	/**
		Appends a ResultSet representation for log to the string buffer.
		@param buff a string buffer (not accept null)
		@param resultSet a ResultSet object (not accept null)
		@return the string buffer
		@throws RuntimeException if an exception thrown from resultSet.beforeFirst method
	*/
	private static StringBuilder append(StringBuilder buff, ResultSet resultSet) {
		try {
			if (resultSet.getType() == ResultSet.TYPE_FORWARD_ONLY) {
				buff.append(resultSet);
			} else {
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();
				int beforeRowNo = resultSet.getRow();
				resultSet.beforeFirst();
				buff.append('[');
				while (resultSet.next()) {
					int rowNo = resultSet.getRow();
					if (rowNo > resultSetMaxRows) {
						buff.append("...");
						break;
					}
					if (rowNo > 1) buff.append(resultSetRowSeparator);
					buff.append('[');
					for (int columnNo = 1; columnNo <= columnCount; ++columnNo) {
						if (columnNo > 1) buff.append(", ");
						if (columnNo > resultSetMaxColumns) {
							buff.append("...");
							break;
						}
						buff.append(metaData.getColumnName(columnNo))
							.append(':')
							.append(toString(resultSet.getObject(columnNo), false, false));
					}
					buff.append(']');
				}
				buff.append(']');
				resultSet.absolute(beforeRowNo);
			}
		}
		catch (SQLException e) {
			try {
				resultSet.beforeFirst();
			}
			catch (SQLException e2) {
				throw new RuntimeException(e);
			}
		}

		return buff;
	}

	/**
		Returns true, if this class or super classes without Object class has toString method.
		@param object an object (not accept null)
		@return true if this class or super classes without Object class has toString method; false otherwise
	*/
	private static boolean hasToString(Object object) {
		boolean result = false;

		Class<?> clazz = object.getClass();
		while (clazz != Object.class) {
			try {
				clazz.getDeclaredMethod("toString");
				result = true;
				break;
			}
			catch (Exception e) {
			}
			clazz = clazz.getSuperclass();
		}

		return result;
	}

	/**
		Returns a string representation of the object uses reflection.
		@param object an object (not accept null)
		@return a string representation
	*/
	private static String toReflectString(Object object) {
		StringBuilder buff = new StringBuilder();

		buff.append('[');
		boolean first = true;
		Class<?> clazz = object.getClass();
		while (clazz != Object.class) {
			// field
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				int modifiers = field.getModifiers();
				if (Modifier.isStatic(modifiers)) continue; // static

				String fieldName = field.getName();

				if (Modifier.isPublic(modifiers)) {
					// public field
					try {
						Object value = field.get(object);
						if (first)
							first = false;
						else
							buff.append(", ");
						buff.append(fieldName).append(':')
							.append(toString(value, field.getType().isPrimitive(), false));
					}
					catch (Exception e) {
					}
				} else {
					// not public field
					Method method = null;
					for (String getterPrefix : getterPrefixes) {
						String methodName = getterPrefix.length() == 0
							? fieldName
							: getterPrefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
						try {
							method = clazz.getDeclaredMethod(methodName);
							if (method.getReturnType() != Void.TYPE)
								break;
							else
								method = null;
						}
						catch (Exception e) {
						}
					}
					if (method != null) {
						try {
							Object value = method.invoke(object);
							if (first)
								first = false;
							else
								buff.append(", ");
							buff.append(fieldName).append(':')
								.append(toString(value, method.getReturnType().isPrimitive(), false));
						}
						catch (Exception e) {
						}
					}
				}
			}

			clazz = clazz.getSuperclass();
		}

		buff.append(']');
		return buff.toString();
	}

}
