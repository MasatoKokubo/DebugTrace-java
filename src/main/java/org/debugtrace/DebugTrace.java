// DebugTrace.java
// (C) 2015 Masato Kokubo

package org.debugtrace;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.debugtrace.logger.Logger;
import org.debugtrace.logger.Std;

/**
 * A utility class for debugging.<br>
 * Call DebugTrace.enter and DebugTrace.leave methods when enter and leave your methods,
 * then outputs execution trace of the program.
 * 
 * @since 1.0.0
 * @author Masato Kokubo
 */
public class DebugTrace {
	private static class State {
		public int nestLevel       = 0; // Nest Level
		public int beforeNestLevel = 0; // Before Nest Level
		public int dataNestLevel   = 0; // Data Nest Level
	}

	// Map for wrppaer classes of primitive type to primitive type
	private static final Map<Class<?>, Class<?>> primitiveTypeMap = new HashMap<>();
	static {
		primitiveTypeMap.put(boolean  .class, boolean.class);
		primitiveTypeMap.put(char     .class, char   .class);
		primitiveTypeMap.put(byte     .class, byte   .class);
		primitiveTypeMap.put(short    .class, short  .class);
		primitiveTypeMap.put(int      .class, int    .class);
		primitiveTypeMap.put(long     .class, long   .class);
		primitiveTypeMap.put(float    .class, float  .class);
		primitiveTypeMap.put(double   .class, double .class);
		primitiveTypeMap.put(Boolean  .class, boolean.class);
		primitiveTypeMap.put(Character.class, char   .class);
		primitiveTypeMap.put(Byte     .class, byte   .class);
		primitiveTypeMap.put(Short    .class, short  .class);
		primitiveTypeMap.put(Integer  .class, int    .class);
		primitiveTypeMap.put(Long     .class, long   .class);
		primitiveTypeMap.put(Float    .class, float  .class);
		primitiveTypeMap.put(Double   .class, double .class);
	}

	// Set of classes that dose not output the type name
	private static final Set<Class<?>> noOutputTypeSet = new HashSet<>();
	static {
		noOutputTypeSet.add(boolean  .class);
		noOutputTypeSet.add(char     .class);
		noOutputTypeSet.add(int      .class);
		noOutputTypeSet.add(String   .class);
		noOutputTypeSet.add(Date     .class);
		noOutputTypeSet.add(Time     .class);
		noOutputTypeSet.add(Timestamp.class);
	}

	// Set of component types of array that dose not output the type name
	private static final Set<Class<?>> noOutputComponentTypeSet = new HashSet<>();
	static {
		noOutputComponentTypeSet.add(boolean  .class);
		noOutputComponentTypeSet.add(char     .class);
		noOutputComponentTypeSet.add(byte     .class);
		noOutputComponentTypeSet.add(short    .class);
		noOutputComponentTypeSet.add(int      .class);
		noOutputComponentTypeSet.add(long     .class);
		noOutputComponentTypeSet.add(float    .class);
		noOutputComponentTypeSet.add(double   .class);
		noOutputComponentTypeSet.add(Boolean  .class);
		noOutputComponentTypeSet.add(Character.class);
		noOutputComponentTypeSet.add(Byte     .class);
		noOutputComponentTypeSet.add(Short    .class);
		noOutputComponentTypeSet.add(Integer  .class);
		noOutputComponentTypeSet.add(Long     .class);
		noOutputComponentTypeSet.add(Float    .class);
		noOutputComponentTypeSet.add(Double   .class);
		noOutputComponentTypeSet.add(String   .class);
		noOutputComponentTypeSet.add(BigInteger.class);
		noOutputComponentTypeSet.add(BigDecimal.class);
		noOutputComponentTypeSet.add(Date     .class);
		noOutputComponentTypeSet.add(Time     .class);
		noOutputComponentTypeSet.add(Timestamp.class);
	}

// 2.4.0
	// Set of element types of Collection that dose not output the type name
	private static final Set<Class<?>> noOutputElementTypeSet = new HashSet<>();
	static {
		noOutputElementTypeSet.add(int      .class);
		noOutputElementTypeSet.add(long     .class);
		noOutputElementTypeSet.add(double   .class);
		noOutputElementTypeSet.add(Boolean  .class);
		noOutputElementTypeSet.add(Character.class);
		noOutputElementTypeSet.add(Integer  .class);
		noOutputElementTypeSet.add(Date     .class);
		noOutputElementTypeSet.add(Time     .class);
		noOutputElementTypeSet.add(Timestamp.class);
	}
////

	// Set of component types of array that output on the single line
	private static final Set<Class<?>> singleLineComponentTypeSet = new HashSet<>();
	static {
		singleLineComponentTypeSet.add(boolean       .class);
		singleLineComponentTypeSet.add(char          .class);
		singleLineComponentTypeSet.add(byte          .class);
		singleLineComponentTypeSet.add(short         .class);
		singleLineComponentTypeSet.add(int           .class);
		singleLineComponentTypeSet.add(long          .class);
		singleLineComponentTypeSet.add(float         .class);
		singleLineComponentTypeSet.add(double        .class);
		singleLineComponentTypeSet.add(Boolean       .class);
		singleLineComponentTypeSet.add(Character     .class);
		singleLineComponentTypeSet.add(Byte          .class);
		singleLineComponentTypeSet.add(Short         .class);
		singleLineComponentTypeSet.add(Integer       .class);
		singleLineComponentTypeSet.add(Long          .class);
		singleLineComponentTypeSet.add(Float         .class);
		singleLineComponentTypeSet.add(Double        .class);
		singleLineComponentTypeSet.add(BigInteger    .class);
		singleLineComponentTypeSet.add(BigDecimal    .class);
	// 2.4.0
		singleLineComponentTypeSet.add(java.util.Date.class);
	////
		singleLineComponentTypeSet.add(Date          .class);
		singleLineComponentTypeSet.add(Time          .class);
		singleLineComponentTypeSet.add(Timestamp     .class);
	}

	// Prefixes of getter methods
	private static final String[] getterPrefixes = {"", "get", "is"};

	// The string part of package of Groovy runtime class
	private static final String groovyRuntimePackage = ".groovy.runtime.";

	// Resources
// 2.4.0
//	private static final Resource resource = new Resource(DebugTrace.class,
//		string -> {
//			if (string != null) {
//				StringBuilder buff = new StringBuilder(string.length());
//				boolean escape = false;
//				for (int index = 0; index < string.length(); ++index) {
//					char ch = string.charAt(index);
//					if (escape) {
//						if      (ch == 't' ) buff.append('\t'); // 09 HT
//						else if (ch == 'n' ) buff.append('\n'); // 0A LF
//						else if (ch == 'r' ) buff.append('\r'); // 0D CR
//						else if (ch == 's' ) buff.append(' ' ); // 20 SPACE
//						else if (ch == '\\') buff.append('\\');
//						else                 buff.append(ch);
//						escape = false;
//					} else {
//						if (ch == '\\')
//							escape = true;
//						else
//							buff.append(ch);
//					}
//				}
//				string = buff.toString();
//			}
//			return string;
//		}
//	);
	private static final Resource resource = new Resource(DebugTrace.class);
////

	private static final String version                 = resource.getString("version"                ); // The version string
	private static final String logLevel                = resource.getString("logLevel"               ); // Log Level
	private static final String enterString             = resource.getString("enterString"            ); // String at enter
	private static final String leaveString             = resource.getString("leaveString"            ); // String at leave
	private static final String threadBoundaryString    = resource.getString("threadBoundaryString"   ); // String of threads boundary
	private static final String classBoundaryString     = resource.getString("classBoundaryString"    ); // String of classes boundary
	private static final String indentString            = resource.getString("indentString"           ); // String of method call indent
	private static final String dataIndentString        = resource.getString("dataIndentString"       ); // String of data indent
	private static final String limitString             = resource.getString("limitString"            ); // String to represent that it has exceeded the limit
	private static final String nonPrintString          = resource.getString("nonPrintString"         ); // String of value in the case of properties that do not display the value (@since 1.5.0)
	private static final String cyclicReferenceString   = resource.getString("cyclicReferenceString"  ); // String to represent that the cyclic reference occurs
	private static final String varNameValueSeparator   = resource.getString("varNameValueSeparator"  ); // Separator between the variable name and value
	private static final String keyValueSeparator       = resource.getString("keyValueSeparator"      ); // Separator between the key and value for Map object
	private static final String fieldNameValueSeparator = resource.getString("fieldNameValueSeparator"); // Separator between the field name and value
	private static final String printSuffixFormat       = resource.getString("printSuffixFormat"      ); // Format string of print suffix
// 2.4.3
//	private static final String indexFormat             = resource.getString("indexFormat"            ); // Format string of index of array and Collection
////
	private static final String utilDateFormat          = resource.getString("utilDateFormat"         ); // Format string of java.util.Date
	private static final String sqlDateFormat           = resource.getString("sqlDateFormat"          ); // Format string of java.sql.Date
	private static final String timeFormat              = resource.getString("timeFormat"             ); // Format string of java.sql.Time
	private static final String timestampFormat         = resource.getString("timestampFormat"        ); // Format string of java.sql.Timestamp
	private static final int    arrayLimit              = resource.getInt   ("arrayLimit"             ); // Limit of array and Collection elements to output
	private static final int    byteArrayLimit          = resource.getInt   ("byteArrayLimit"         ); // Limit of byte array elements to output
	private static final int    mapLimit                = resource.getInt   ("mapLimit"               ); // Limit of Map elements to output
	private static final int    stringLimit             = resource.getInt   ("stringLimit"            ); // Limit of String characters to output
// 2.4.3
//	private static final int    outputIndexLength       = resource.getInt   ("outputIndexLength"      ); // Length of array and Collection to output index
////

// 2.4.0
//	// since 2.2.0
//	private static final List<String> nonPrintProperties = resource.getStrings("nonPrintProperties"   ); // Non print properties (<class name>#<property name>)
	private static List<String> nonPrintProperties      = resource.getStringList("nonPrintProperties" ); // Non print properties (<class name>#<property name>)
////

	// since 2.3.0
	private static final String defaultPackage          = resource.getString("defaultPackage", ""     ); // Default package part
	private static final String defaultPackageString    = resource.getString("defaultPackageString"   ); // String replacing the default package part

	// since 2.4.0
	private static final List<String> reflectionClasses = resource.getStringList("reflectionClasses"  ); // List of class names that output content in reflection even if toString method is implemented
	private static final Map<String, String> mapNameMap = resource.getStringKeyMap("mapNameMap"       ); // Name to mapNmae map 

	// Logger
	private static Logger logger = null;

	static {
		String loggerName = null;
		try {
			loggerName = resource.getString("logger");
			if (loggerName != null) {
				if (loggerName.indexOf('.') == -1)
					loggerName = Logger.class.getPackage().getName() + '.' + loggerName;
				logger = (Logger)Class.forName(loggerName).newInstance();
			}
		}
		catch (Exception e) {
			System.err.println("DebugTrace: " + e.toString() + "(" + loggerName + ")");
		}

		if (logger == null)
			logger = new Std.Out();

		// Set a logging level
		logger.setLevel(logLevel);
	}

	// Whether tracing is enabled
	private static final boolean enabled = logger.isEnabled();

	// Array of indent strings
	private static final String[] indentStrings = new String[64];
	static {
		indentStrings[0] = "";
		IntStream.iterate(1, index -> index + 1).limit(indentStrings.length - 1)
			.forEach(index -> indentStrings[index] = indentStrings[index - 1] + indentString);
	}

	// Array of data indent strings
	private static final String[] dataIndentStrings = new String[64];
	static {
		dataIndentStrings[0] = "";
		IntStream.iterate(1, index -> index + 1).limit(dataIndentStrings.length - 1)
			.forEach(index -> dataIndentStrings[index] = dataIndentStrings[index - 1] + dataIndentString);
	}

	// Map of thread id to the indent state
	private static final Map<Long,  State> stateMap = new HashMap<>();

	// Before thread id
	private static long beforeThreadId;

// 2.4.0
//	// Reflection target map
//	private static final Map<Class<?>, Boolean> reflectionTargetMap = new HashMap<>();
////

	// Reflected object list
	private static final List<Object> reflectedObjects = new ArrayList<>();

	// Non-printing property map (@since 1.5.0)
// 2.3.0
//	private static final Map<String, Boolean> nonPrintPropertyMap = new HashMap<>();
// 2.4.0
//	private static final Set<String> nonPrintPropertySet = new HashSet<>();
////

	static {
	// 2.1.0
	//	logger.log("DebugTrace " + version + " / logger: " + logger.getClass().getSimpleName());
		logger.log("DebugTrace " + version + " / logger: " + logger.getClass().getName());
	////

	// 2.2.0
	// 2.4.0
	//	// Non print properties
	//	for (String nonPrintProperty : nonPrintProperties) {
	//		try {
	//			int sharpIndex = nonPrintProperty.indexOf('#');
	//			if (sharpIndex < 0) {
	//				logger.log("ERROR: " + nonPrintProperty);
	//				continue;
	//			}
	//			String className = nonPrintProperty.substring(0, sharpIndex);
	//			String propertyName = nonPrintProperty.substring(sharpIndex + 1);
	//			Class<?> targetClass = Class.forName(className);
	//			addNonPrintProperties(targetClass, propertyName);
	//		}
	//		catch (Exception e) {
	//			logger.log("ERROR: " + nonPrintProperty + ": " + e.toString());
	//		}
	//	}
	////
	}

// 2.4.0
	private static final Map<String, Map<Integer, String>> convertMapMap = new HashMap<>();
	private static String lastLog = "";
////

	private DebugTrace() {}

	/**
	 * Append a timestamp to the head of the string.<br>
	 * <i>This method is used internally.</i>
	 *
	 * @param string a string
	 * @return a string appended a timestamp string
	 */
	public static String appendTimestamp(String string) {
		return String.format(timestampFormat, new Timestamp(System.currentTimeMillis())) + " " + string;
	}

	/**
	 * Returns indent state.
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
	 * Returns whether tracing is enabled.
	 *
	 * @return true if tracing is enabled; false otherwise
	 */
	public static boolean isEnabled() {return enabled;}

	/**
	 * Returns a string corresponding to the current indent.
	 *
	 * @return A string corresponding to the current indent
	 */
	private static String getIndentString(State state) {
		return indentStrings[
			state.nestLevel < 0 ? 0 :
			state.nestLevel >= indentStrings.length ? indentStrings.length - 1
				: state.nestLevel]
			+ dataIndentStrings[
			state.dataNestLevel < 0 ? 0 :
			state.dataNestLevel >= dataIndentStrings.length ? dataIndentStrings.length - 1
				: state.dataNestLevel];
	}

// 2.4.0
//	/**
//	 * Add a reflection target class.
//	 *
//	 * @param targetClass a reflection target class.
//	 */
//	public static void addReflectionTarget(Class<?> targetClass) {
//		synchronized(stateMap) {
//			reflectionTargetMap.put(targetClass, true);
//		}
//	}
////

// 2.4.0
//	/**
//	 * Specifies properties that do not display the value.
//	 *
//	 * @param targetClass a target class.
//	 * @param propertyNames target property names.
//	 *
//	 * @since 1.5.0
//	 */
//	public static void addNonPrintProperties(Class<?> targetClass, String... propertyNames) {
//		String prefix = targetClass.getName() + ".";
//		synchronized(stateMap) {
//			Arrays.stream(propertyNames)
//			// 2.3.0
//			//	.forEach(propertyName -> nonPrintPropertyMap.put(prefix + propertyName, true));
//				.forEach(propertyName -> nonPrintPropertySet.add(prefix + propertyName));
//			////
//		}
//	}
////

	/**
	 * Up the nest level.
	 *
	 * @param state a nest status of current thread
	 */
	private static void upNest(State state) {
		state.beforeNestLevel = state.nestLevel;
		++state.nestLevel;
	}

	/**
	 * Down the nest level.
	 *
	 * @param state a nest status of current thread
	 */
	private static void downNest(State state) {
		state.beforeNestLevel = state.nestLevel;
		--state.nestLevel;
	}

	/**
	 * Up the data nest level.
	 *
	 * @param state a nest status of current thread
	 *
	 * @since 1.4.0
	 */
	private static void upDataNest(State state) {
		++state.dataNestLevel;
	}

	/**
	 * Down the data nest level.
	 *
	 * @param state a nest status of current thread
	 *
	 * @since 1.4.0
	 */
	private static void downDataNest(State state) {
		--state.dataNestLevel;
	}

	/**
	 * Common start processing of output.
	 */
	private static void printStart() {
		Thread thread = Thread.currentThread();
		long threadId = thread.getId();
		if (threadId !=  beforeThreadId) {
			// Thread changing
			logger.log(""); // Line break
			logger.log(String.format(threadBoundaryString, thread.getName(), threadId));
			logger.log(""); // Line break

			beforeThreadId = threadId;
		}
	}

	/**
	 * Common end processing of output.
	 */
	private static void printEnd() {
		beforeThreadId = Thread.currentThread().getId();
	}

	/**
	 * Call this method at entrance of your methods.
	 */
	public static void enter() {
		if (enabled) {
			synchronized(stateMap) {
				printStart(); // Common start processing of output

				State state = getState();
				if (state.beforeNestLevel > state.nestLevel)
				// 2.1.0
				//	logger.log(""); // Line break
					logger.log(getIndentString(state)); // Line break
				////

			// 2.4.0
			//	logger.log(getIndentString(state) + getCallerInfo(enterString));
				lastLog = getIndentString(state) + getCallerInfo(enterString);
				logger.log(lastLog);
			////

				upNest(state);
			}
		}
	}

	/**
	 * Call this method at exit of your methods.
	 */
	public static void leave() {
		if (enabled) {
			synchronized(stateMap) {
				printStart(); // Common start processing of output

				State state = getState();
				downNest(state);

			// 2.4.0
			//	logger.log(getIndentString(state) + getCallerInfo(leaveString));
				lastLog = getIndentString(state) + getCallerInfo(leaveString);
				logger.log(lastLog);
			////

				printEnd(); // Common end processing of output
			}
		}
	}

	/**
	 * Returns a string of the caller information.
	 */
	private static String getCallerInfo(String baseString) {
		StackTraceElement element = getStackTraceElement();
		return String.format(baseString,
		// 2.3.0
		//	element.getClassName(),
			replaceTypeName(element.getClassName()),
		////
			element.getMethodName(),
			element.getFileName(),
			element.getLineNumber());
	}

	/**
	 * Outputs the message to the log.
	 *
	 * @param message a message
	 */
	public static void print(String message) {
		if (enabled)
			printSub(message);
	}

	/**
	 * Outputs a message to the log.
	 *
	 * @param messageSupplier a message supplier
	 */
	public static void print(Supplier<String> messageSupplier) {
		if (enabled)
			printSub(messageSupplier.get());
	}

	/**
	 * Outputs the message to the log.
	 *
	 * @param message a message
	 */
	private static void printSub(String message) {
		synchronized(stateMap) {
			printStart(); // Common start processing of output

		// 2.4.0
		//	if (message.isEmpty())
		//		logger.log("");
		//	else {
			String lastLog = "";
			if (!message.isEmpty()) {
		////
				StackTraceElement element = getStackTraceElement();
				String suffix = String.format(printSuffixFormat,
				// 2.3.0
				//	element.getClassName(),
					replaceTypeName(element.getClassName()),
				////
					element.getMethodName(),
					element.getFileName(),
					element.getLineNumber());
			// 2.4.0
			//	logger.log(getIndentString(getState()) + message + suffix);
				lastLog = getIndentString(getState()) + message + suffix;
			////
			}
		// 2.4.0
			logger.log(lastLog);
		////
			printEnd(); // Common end processing of output
		}
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param value the value to output (accept null)
	 * @param isPrimitive true if the value is primitive type, false otherwise
	 */
// 2.4.0
//	private static void printSub(String name, Object value, boolean isPrimitive) {
	private static void printSub(String mapName, String name, Object value, boolean isPrimitive) {
////
		synchronized(stateMap) {
			printStart(); // Common start processing of output

			reflectedObjects.clear();

			State state = getState();
			List<String> strings = new ArrayList<>();
			StringBuilder buff = new StringBuilder();

			buff.append(name).append(varNameValueSeparator);
		// 2.4.0
		//	append(state, strings, buff, value, isPrimitive, false);
			if (mapName == null) {
			// 2.4.2
			//	mapName = mapNameMap.get(name);
				String normalizedName = name.substring(name.lastIndexOf('.') + 1).trim();
				normalizedName = normalizedName.substring(normalizedName.lastIndexOf(' ') + 1);
				mapName = mapNameMap.get(normalizedName);
			////
			}
			append(state, strings, buff, mapName, value, isPrimitive, false, false);
		////

			StackTraceElement element = getStackTraceElement();
			String suffix = String.format(printSuffixFormat,
				element.getClassName(),
				element.getMethodName(),
				element.getFileName(),
				element.getLineNumber());
			buff.append(suffix);
			lineFeed(state, strings, buff);

			strings.stream().forEach(logger::log);
		// 2.4.0
			lastLog = String.join("\n", strings);
		////

			printEnd(); // Common end processing of output
		}
	}

	/**
	 * Returns a caller stack trace element.
	 *
	 * @returns a caller stack trace element
	 */
	private static StackTraceElement getStackTraceElement() {
		StackTraceElement result = null;

		String myClassName = DebugTrace.class.getName();

		StackTraceElement[] elements = new Throwable().getStackTrace();
		for (int index = 3; index < elements.length; ++index) {
			StackTraceElement element = elements[index];
			String className = element.getClassName();
			if (className.indexOf(myClassName) == -1 && className.indexOf(groovyRuntimePackage) == -1) {
				result = element;
				break;
			}
		}

		return result;
	}

	/**
	 * Line Feed.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	*/
	private static void lineFeed(State state, List<String> strings, StringBuilder buff) {
		strings.add(getIndentString(getState()) + buff.toString());
		buff.setLength(0);
	}

	/**
	 * Outputs the name and the boolean value to the log.
	 *
	 * @param name the name of the value
	 * @param value the boolean value to output
	 */
	public static void print(String name, boolean value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and the char value to the log.
	 *
	 * @param name the name of the value
	 * @param value the char value to output
	 */
	public static void print(String name, char value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and the byte value to the log.
	 *
	 * @param name the name of the value
	 * @param value the byte value to output
	 */
	public static void print(String name, byte value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and the byte value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param value the byte value to output
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, byte value) {
		if (enabled)
			printSub(mapName, name, value, true);
	}

	/**
	 * Outputs the name and the short value to the log.
	 *
	 * @param name the name of the value
	 * @param value the short value to output
	 */
	public static void print(String name, short value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and the short value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param value the short value to output
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, short value) {
		if (enabled)
			printSub(mapName, name, value, true);
	}

	/**
	 * Outputs the name and the int value to the log.
	 *
	 * @param name the name of the value
	 * @param value the int value to output
	 */
	public static void print(String name, int value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and the int value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param value the int value to output
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, int value) {
		if (enabled)
			printSub(mapName, name, value, true);
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param name the name of the value
	 * @param value the long value to output
	 */
	public static void print(String name, long value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and the long value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param value the long value to output
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, long value) {
		if (enabled)
			printSub(mapName, name, value, true);
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param name the name of the value
	 * @param value the float value to output
	 */
	public static void print(String name, float value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param name the name of the value
	 * @param value the double value to output
	 */
	public static void print(String name, double value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, true);
		////
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param name the name of the value
	 * @param value the value to output (accept null)
	 */
	public static void print(String name, Object value) {
		if (enabled)
		// 2.4.0
		//	printSub(name, value, true);
			printSub(null, name, value, false);
		////
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param value the value to output (accept null)
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, Object value) {
		if (enabled)
			printSub(mapName, name, value, false);
	}

	/**
	 * Outputs the name and boolean value to the log.
	 *
	 * @param name the name of the value
	 * @param valueSupplier the supplier of boolean value to output
	 */
	public static void print(String name, BooleanSupplier valueSupplier) {
		if (enabled)
		// 2.4.0
		//	printSub(name, valueSupplier.getAsBoolean(), true);
			printSub(null, name, valueSupplier.getAsBoolean(), true);
		////
	}

	/**
	 * Outputs a int value to the log.
	 *
	 * @param name the name of the value
	 * @param valueSupplier the supplier of int value to output
	 */
	public static void print(String name, IntSupplier valueSupplier) {
		if (enabled)
		// 2.4.0
		//	printSub(name, valueSupplier.getAsInt(), true);
			printSub(null, name, valueSupplier.getAsInt(), true);
		////
	}

	/**
	 * Outputs a int value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param valueSupplier the supplier of int value to output
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, IntSupplier valueSupplier) {
		if (enabled)
			printSub(mapName, name, valueSupplier.getAsInt(), true);
	}

	/**
	 * Outputs a long value to the log.
	 *
	 * @param name the name of the value
	 * @param valueSupplier the supplier of long value to output
	 */
	public static void print(String name, LongSupplier valueSupplier) {
		if (enabled)
		// 2.4.0
		//	printSub(name, valueSupplier.getAsLong(), true);
			printSub(null, name, valueSupplier.getAsLong(), true);
		////
	}

	/**
	 * Outputs a long value to the log.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param valueSupplier the supplier of long value to output
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, LongSupplier valueSupplier) {
		if (enabled)
			printSub(mapName, name, valueSupplier.getAsLong(), true);
	}

	/**
	 * Outputs a double value to the log.
	 *
	 * @param name the name of the value
	 * @param valueSupplier the supplier of double value to output
	 */
	public static void print(String name, DoubleSupplier valueSupplier) {
		if (enabled)
		// 2.4.0
		//	printSub(name, valueSupplier.getAsDouble(), true);
			printSub(null, name, valueSupplier.getAsDouble(), true);
		////
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param <T> type of the value
	 * @param name the name of the value
	 * @param valueSupplier the supplier of value to output
	*/
	public static <T> void print(String name, Supplier<T> valueSupplier) {
		if (enabled)
		// 2.4.0
		//	printSub(name, valueSupplier.get(), false);
			printSub(null, name, valueSupplier.get(), false);
		////
	}

	/**
	 * Outputs the name and value to the log.
	 *
	 * @param <T> type of the value
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param name the name of the value
	 * @param valueSupplier the supplier of value to output
	*/
	public static <T> void print(String mapName, String name, Supplier<T> valueSupplier) {
		if (enabled)
			printSub(mapName, name, valueSupplier.get(), false);
	}

	/**
	 * Appends the value for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param value the value object
	 * @param isPrimitive true if the value is primitive type, false otherwise
	 * @param isComponent true if the value is component of an array, false otherwise
	 * @param isElement true if the value is element of a container class, false otherwise
	 */
// 2.4.0
//	private static void append(State state, List<String> strings, StringBuilder buff, Object value, boolean isPrimitive, boolean isComponent) {
	private static void append(State state, List<String> strings, StringBuilder buff,
		String mapName, Object value, boolean isPrimitive, boolean isComponent, boolean isElement) {
////
		if (value == null) {
			buff.append("null");
		} else {
			Class<?> type = value.getClass();
			if (isPrimitive) {
				type = primitiveTypeMap.get(type);
				if (type == null)
					type = value.getClass();
			}

		// 2.4.0
		//	String typeName = getTypeName(type, value, isComponent, 0);
			String typeName = getTypeName(type, value, isComponent, isElement, 0);
		////
			if (typeName != null)
				buff.append(typeName);

			if (type.isArray()) {
				// Array
				if      (type == char[].class) append(state, strings, buff, ((char[])value)); // char Array
				else if (type == byte[].class) append(state, strings, buff, ((byte[])value)); // byte Array
			// 2.4.0
			//	else                      appendArray(state, strings, buff,          value ); // Other Array
				else                      appendArray(state, strings, buff, mapName, value ); // Other Array
			////

			} else if (value instanceof Boolean) {
				// Boolean
				buff.append(value);

			} else if (value instanceof Character) {
				// Character
				buff.append('\'');
				append(state, strings, buff, ((Character)value).charValue());
				buff.append('\'');

			} else if (value instanceof Number) {
				// Number
				if (value instanceof BigDecimal) buff.append(((BigDecimal)value).toPlainString()); // BigDecimal
				else buff.append(value); // Other Number

			} else if (value instanceof CharSequence) {
				// CharSequence
				append(state, strings, buff, (CharSequence)value);

			} else if (value instanceof java.util.Date) {
				// Date
				if      (value instanceof Date     ) buff.append(String.format(sqlDateFormat  , value)); // sql Date
				else if (value instanceof Time     ) buff.append(String.format(timeFormat     , value)); // Time
				else if (value instanceof Timestamp) buff.append(String.format(timestampFormat, value)); // Timestamp
				else                                 buff.append(String.format(utilDateFormat , value)); // Other Date

			} else if (value instanceof OptionalInt) {
				// OptionalInt
				if (((OptionalInt)value).isPresent())
				// 2.4.0
				//	append(state, strings, buff, ((OptionalInt)value).getAsInt(), true, true);
					append(state, strings, buff, mapName, ((OptionalInt)value).getAsInt(), true, false, true);
				////
				else
					buff.append("empty");

			} else if (value instanceof OptionalLong) {
				// OptionalLong
				if (((OptionalLong)value).isPresent())
				// 2.4.0
				//	append(state, strings, buff, ((OptionalLong)value).getAsLong(), true, true);
					append(state, strings, buff, mapName, ((OptionalLong)value).getAsLong(), true, false, true);
				////
				else
					buff.append("empty");

			} else if (value instanceof OptionalDouble) {
				// OptionalDouble
				if (((OptionalDouble)value).isPresent())
				// 2.4.0
				//	append(state, strings, buff, ((OptionalDouble)value).getAsDouble(), true, true);
					append(state, strings, buff, mapName, ((OptionalDouble)value).getAsDouble(), true, false, true);
				////
				else
					buff.append("empty");

			} else if (value instanceof Optional) {
				// Optional
				if (((Optional<?>)value).isPresent())
				// 2.4.0
				//	append(state, strings, buff, ((Optional<?>)value).get(), false, true);
					append(state, strings, buff, mapName, ((Optional<?>)value).get(), false, false, true);
				////
				else
					buff.append("empty");

			} else if (value instanceof Collection) {
				// Collection
			// 2.4.0
			//	append(state, strings, buff, (Collection<?>)value);
				append(state, strings, buff, mapName, (Collection<?>)value);
			////

			} else if (value instanceof Map) {
				// Map
			// 2.4.0
			//	append(state, strings, buff, (Map<?,?>)value);
				append(state, strings, buff, mapName, (Map<?,?>)value);
			////

			} else if (value instanceof Clob) {
				// Clob
				try {
					long length = ((Clob)value).length();
					if (length > (long)stringLimit)
						length = (long)(stringLimit + 1);
					append(state, strings, buff, ((Clob)value).getSubString(1L, (int)length));
				}
				catch (SQLException e) {
					buff.append(e);
				}

			} else if (value instanceof Blob) {
				// Blob
				try {
					long length = ((Blob)value).length();
					if (length > (long)byteArrayLimit)
						length = (long)(byteArrayLimit + 1);
					append(state, strings, buff, ((Blob)value).getBytes(1L, (int)length));
				}
				catch (SQLException e) {
					buff.append(e);
				}

			} else {
				// Other
			// 2.4.0
			//	Boolean isReflection = reflectionTargetMap.get(type);
			//	if (isReflection == null) {
			//		isReflection = !hasToString(type);
			//		reflectionTargetMap.put(type, isReflection);
			//	}
				boolean isReflection = reflectionClasses.contains(type.getName());
				if (!isReflection && !hasToString(type)) {
					isReflection = true;
					reflectionClasses.add(type.getName());
				}
			////

				if (isReflection) {
					// Use Reflection
					if (reflectedObjects.stream().anyMatch(object -> value == object))
						// Cyclic reference
						buff.append(cyclicReferenceString).append(value);
					else {
						// Use Reflection
						reflectedObjects.add(value);
						appendReflectString(state, strings, buff, value);
						reflectedObjects.remove(reflectedObjects.size() - 1);
					}
				} else {
					// Use toString method
					buff.append(value);
				}
			}
		// 2.4.0
			String convertedValue =  getConvertedValue(mapName, value);
			if (convertedValue != null)
				buff.append('(').append(convertedValue).append(')');
		////
		}
	}

	/**
	 * Returns the type name to be output to the log.<br>
	 * If dose not output, returns null.
	 *
	 * @param type the type of the value
	 * @param value the value object
	 * @param isComponent true if the value is component of an array, false otherwise
	 * @param isElement true if the value is element of a container class, false otherwise
	 * @param nest current nest count
	 * @return the type name to be output to the log
	*/
	@SuppressWarnings("rawtypes")
// 2.4.0
//	private static String getTypeName(Class<?>type, Object value, boolean isComponent, int nest) {
	private static String getTypeName(Class<?>type, Object value, boolean isComponent, boolean isElement, int nest) {
////
		String typeName = null;
		long length = -1L;
		int  size   = -1;

		if (type.isArray()) {
			// Array
			typeName = getTypeName(type.getComponentType(), null, false, false, nest + 1) + "[]";
			if (value != null)
				length = Array.getLength(value);
		} else {
			// Not Array
			if (   nest > 0
				|| (isComponent
						? !noOutputComponentTypeSet.contains(type)
					: isElement
						? !noOutputElementTypeSet.contains(type)
						: !noOutputTypeSet.contains(type))
				) {
				// Output the type name
				typeName = type.getCanonicalName();
				if (typeName == null)
					typeName = type.getName();
			// 2.4.0
			//	if (typeName.startsWith("java.") && !typeName.equals("java.util.Date"))
				if (   typeName.startsWith("java.lang.")
					|| typeName.startsWith("java.math.")
					|| typeName.startsWith("java.sql.")
					|| typeName.startsWith("java.util.") && !typeName.equals("java.util.Date"))
			////
					typeName = type.getSimpleName();
			// 2.3.0
				else
					typeName = replaceTypeName(typeName);
			////

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
	 * Replace a class name.
	 *
	 * @param className a class name
	 * @return the replaced ckass name
	 *
	 * @since 2.3.0
	 */
	private static String replaceTypeName(String typeName) {
		if (!defaultPackage.isEmpty() && typeName.startsWith(defaultPackage))
			typeName = defaultPackageString + typeName.substring(defaultPackage.length());
		return typeName;
	}

	/**
	 * Returns the map for converting.
	 *
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param value the value object
	 * @return a converted value (mey be null)
	 *
	 * @since 2.4.0
	 */
	private static String getConvertedValue(String mapName, Object value) {
		if (mapName == null || value == null)
			return null;

		Integer key = null;
		if (value instanceof Byte)
			key = (int)(byte)value;
		else if (value instanceof Short)
			key = (int)(short)value;
		else if (value instanceof Integer)
			key = (Integer)value;
		else if (value instanceof Long) {
			if ((long)value >= Integer.MIN_VALUE && (long)value <= Integer.MAX_VALUE)
				key = (int)(long)value;
		}
		if (key == null)
			return null;

		Map<Integer, String> convertMap = convertMapMap.get(mapName);
		if (convertMap == null) {
			convertMap = resource.getIntegerKeyMap(mapName);
			convertMapMap.put(mapName, convertMap);
		}

		return convertMap.get(key);
	}

	/**
	 * Appends a character representation for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param ch a character
	 */
	private static void append(State state, List<String> strings, StringBuilder buff, char ch) {
		if (ch >= ' ' && ch != '\u007F') {
		// 2.4.0
		//	if      (ch == '"' ) buff.append("\\\"");
		//	else if (ch == '\'') buff.append("\\'" );
			if      (ch == '\'') buff.append("\\'" );
		////
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
	}

	/**
	 * Appends a CharSequence representation for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param charSequence a CharSequence object
	 */
	private static void append(State state, List<String> strings, StringBuilder buff, CharSequence charSequence) {
		buff.append('"');
		for (int index = 0; index < charSequence.length(); ++index) {
			if (index >= stringLimit) {
				buff.append(limitString);
				break;
			}
		// 2.4.0
		//	append(state, strings, buff, charSequence.charAt(index));
			char ch = charSequence.charAt(index);
			if (ch >= ' ' && ch != '\u007F') {
				if      (ch == '"' ) buff.append("\\\"");
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
		////
		}
		buff.append('"');
	}

	/**
	 * Appends a character array representation for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param chars a character array
	 */
	private static void append(State state, List<String> strings, StringBuilder buff, char[] chars) {
		buff.append('"');
		for (int index = 0; index < chars.length; ++index) {
			if (index >= stringLimit) {
				buff.append(limitString);
				break;
			}
			append(state, strings, buff, chars[index]);
		}
		buff.append('"');
	}

	/**
	 * Appends a byte array representation for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param bytes a byte array
	 */
	private static void append(State state, List<String> strings, StringBuilder buff, byte[] bytes) {
		boolean multiLine = bytes.length > 16 && byteArrayLimit > 16;

		buff.append('[');
		if (multiLine) {
			lineFeed(state, strings, buff);
			upDataNest(state);
		}

		int offset = 0;
		for (int index = 0; index < bytes.length; ++index) {
			if (offset > 0) buff.append(" ");

			if (index >= byteArrayLimit) {
				buff.append(limitString);
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
			++offset;

			if (multiLine && offset == 16) {
				lineFeed(state, strings, buff);
				offset = 0;
			}
		}

		if (multiLine) {
			if (buff.length() > 0)
				lineFeed(state, strings, buff);
			downDataNest(state);
		}
		buff.append(']');
	}

	/**
	 * Appends an object array representation for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param array an object array
	 */
// 2.4.0
//	private static void appendArray(State state, List<String> strings, StringBuilder buff, Object array) {
	private static void appendArray(State state, List<String> strings, StringBuilder buff, String mapName, Object array) {
////
		Class<?> componentType = array.getClass().getComponentType();

		int length = Array.getLength(array);

		boolean multiLine = length >= 2
			&& !singleLineComponentTypeSet.contains(componentType)
		// 2.4.0
			&& !(Enum.class.isAssignableFrom(componentType))
		////
			;

		buff.append('[');
		if (multiLine) {
			lineFeed(state, strings, buff);
			upDataNest(state);
		}

		for (int index = 0; index < length; ++index) {
			if (!multiLine && index > 0) buff.append(", ");

			if (index < arrayLimit) {
			// 2.4.3
			//	if (length >= outputIndexLength)
			//		buff.append(String.format(indexFormat, index));
			////
				Object value = Array.get(array, index);
			// 2.4.0
			//	append(state, strings, buff, value, componentType.isPrimitive(), true);
				append(state, strings, buff, mapName, value, componentType.isPrimitive(), true, false);
			////
			} else
				buff.append(limitString);

			if (multiLine) {
				buff.append(",");
				lineFeed(state, strings, buff);
			}

			if (index >= arrayLimit) break;
		}

		if (multiLine)
			downDataNest(state);
		buff.append(']');
	}

	/**
	 * Appends a Collection representation for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param collection a Collection object
	 */
// 2.4.0
//	private static void append(State state, List<String> strings, StringBuilder buff, Collection<?> collection) {
//	Iterator<?> iterator = collection.iterator();
	private static <E> void append(State state, List<String> strings, StringBuilder buff, String mapName, Collection<E> collection) {
		Iterator<E> iterator = collection.iterator();
////

		boolean multiLine = collection.size() >= 2;

		buff.append('[');
	// 2.4.0
	//	if (multiLine) {
	//		lineFeed(state, strings, buff);
	//		upDataNest(state);
	//	}
	////

		for (int index = 0; iterator.hasNext(); ++index) {
		// 2.4.0
			E element = iterator.next();
			if (index == 0 && element != null) {
				if (   singleLineComponentTypeSet.contains(element.getClass())
					|| Enum.class.isAssignableFrom(element.getClass()))
					multiLine = false;
				if (multiLine) {
					lineFeed(state, strings, buff);
					upDataNest(state);
				}
			}
		////

			if (!multiLine && index > 0) buff.append(", ");

			if (index < arrayLimit) {
			// 2.4.3
			//	if (collection.size() >= outputIndexLength)
			//		buff.append(String.format(indexFormat, index));
			////
			// 2.4.0
			//	append(state, strings, buff, iterator.next(), false, false);
				append(state, strings, buff, mapName, element, false, false, true);
			////
			} else
				buff.append(limitString);

			if (multiLine) {
				buff.append(",");
				lineFeed(state, strings, buff);
			}

			if (index >= arrayLimit) break;
		}

		if (multiLine)
			downDataNest(state);
		buff.append(']');
	}

	/**
	 * Appends a Map representation for logging to the string buffer.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param buff a string buffer
	 * @param mapName the name of the map for get a constant name corresponding to the value (accept null)
	 * @param map a Map
	 */
// 2.4.0
//	private static <K,V> void append(State state, List<String> strings, StringBuilder buff, Map<K,V> map) {
		private static <K,V> void append(State state, List<String> strings, StringBuilder buff, String mapName, Map<K,V> map) {
////
		Iterator<Map.Entry<K,V>> iterator = map.entrySet().iterator();

		boolean multiLine = map.size() >= 2;

		buff.append('[');
	// 2.4.0
	//	if (multiLine) {
	//		lineFeed(state, strings, buff);
	//		upDataNest(state);
	//	}
	////

		for (int index = 0; iterator.hasNext(); ++index) {
		// 2.4.0
			Map.Entry<K,V> entry = iterator.next();
			K key   = entry.getKey();
			V value = entry.getValue();
			if (index == 0) {
				if (   key   != null && singleLineComponentTypeSet.contains(key  .getClass())
					&& value != null && singleLineComponentTypeSet.contains(value.getClass()))
					multiLine = false;
				if (multiLine) {
					lineFeed(state, strings, buff);
					upDataNest(state);
				}
			}
		////
			if (!multiLine && index > 0) buff.append(", ");

			if (index < mapLimit) {
			// 2.4.0
			//	Map.Entry<K,V> entry = iterator.next();
			//	append(state, strings, buff, entry.getKey(), false, false);
				append(state, strings, buff, mapName, key, false, false, true);
			////
				buff.append(keyValueSeparator);
			// 2.4.0
			//	append(state, strings, buff, entry.getValue(), false, false);
				append(state, strings, buff, mapName, value, false, false, true);
			////
			} else
				buff.append(limitString);

			if (multiLine) {
				buff.append(",");
				lineFeed(state, strings, buff);
			}

			if (index >= mapLimit) break;
		}

		if (multiLine)
			downDataNest(state);
		buff.append(']');
	}

	/**
	 * Returns true, if this class or super classes without Object class has toString method.
	 *
	 * @param object an object
	 * @return true if this class or super classes without Object class has toString method; false otherwise
	 */
	private static boolean hasToString(Class<?> clazz) {
		boolean result = false;

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
	 * Returns a string representation of the object uses reflection.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param object an object
	 */
	private static void appendReflectString(State state, List<String> strings, StringBuilder buff, Object object) {
		buff.append('[');
		lineFeed(state, strings, buff);
		upDataNest(state);

		Class<?> clazz = object.getClass();
		appendReflectStringSub(state, strings, buff, object, clazz, clazz.getSuperclass() != Object.class);

		downDataNest(state);
		buff.append(']');
	}

	/**
	 * Returns a string representation of the object uses reflection.
	 *
	 * @param state indent state
	 * @param strings a string list
	 * @param object an object
	 * @param clazz the class of the object
	 * @param extended the class is extended
	 */
	private static void appendReflectStringSub(State state, List<String> strings, StringBuilder buff, Object object, Class<?> clazz, boolean extended) {
		if (clazz == Object.class)
			return;

		// Call for the super class
		appendReflectStringSub(state, strings, buff, object, clazz.getSuperclass(), extended);

		if (extended) {
		// 2.3.1
		//	buff.append(String.format(classBoundaryString, clazz.getCanonicalName()));
			String className = clazz.getCanonicalName();
			if (className == null)
				className = clazz.getName();
			buff.append(String.format(classBoundaryString, replaceTypeName(className)));
		////
			lineFeed(state, strings, buff);
		}

	// 2.4.0
	//	String classNamePrefix = clazz.getName() + ".";
		String classNamePrefix = clazz.getName() + "#";
	////

		// field
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			int modifiers = field.getModifiers();
			if (Modifier.isStatic(modifiers)) continue; // static

			String fieldName = field.getName();

			Object value = null;
			Method method = null;

			if (!Modifier.isPublic(modifiers)) {
				// non public field
				for (String getterPrefix : getterPrefixes) {
					String methodName = getterPrefix.length() == 0
						? fieldName
						: getterPrefix + fieldName.substring(0, 1).toUpperCase(Locale.ENGLISH) + fieldName.substring(1);
					try {
						method = clazz.getDeclaredMethod(methodName);
						if (method.getReturnType() == field.getType())
							break;
						else
							method = null;
					}
					catch (Exception e) {
					}
				}
			}

			try {
				if (method != null) {
					// has a getter method
					if (!Modifier.isPublic(method.getModifiers()))
						// non public method
						method.setAccessible(true);
					value = method.invoke(object);
				} else {
					// does not have a getter method
					if (!Modifier.isPublic(modifiers))
						// non public field
						field.setAccessible(true);
					value = field.get(object);
				}
			}
			catch (Exception e) {
				value = "<" + e + ">";
			}

			buff.append(fieldName).append(fieldNameValueSeparator);

		// 2.3.0
		//	if (value != null && nonPrintPropertyMap.containsKey(classNamePrefix + fieldName))
		// 2.4.0
		//	if (value != null && nonPrintPropertySet.contains(classNamePrefix + fieldName))
			if (value != null && nonPrintProperties.contains(classNamePrefix + fieldName))
		////
				// the property is non-printing and the value is not null
				buff.append(nonPrintString);
			else {
			// 2.4.0
			//	append(state, strings, buff, value, field.getType().isPrimitive(), false);
				String mapName = mapNameMap.get(fieldName);
				append(state, strings, buff, mapName, value, field.getType().isPrimitive(), false, false);
			////
			}

			buff.append(",");
			lineFeed(state, strings, buff);
		}
	}

	/**
	 * Returns the last log string output.
	 *
	 * @return the last log string output.
	 *
	 * @since 2.4.0
	 */
	public static String getLastLog() {
		return lastLog;
	}
}
