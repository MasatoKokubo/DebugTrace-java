/*
	SLF4JLogger.java

	Created on 2015/07/04.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.log4j.Level;
import org.slf4j.LoggerFactory;

/**
	A logger implementation using Log4J2.
	@since 1.0.0
	@author Masato Kokubo
*/
public class SLF4JLogger implements Logger {
	// Level Map
	private static final Map<String, Level> levelMap = new HashMap<>();
	static {
		levelMap.put("default", Level.TRACE);
		levelMap.put("trace"  , Level.TRACE);
		levelMap.put("debug"  , Level.DEBUG);
		levelMap.put("info"   , Level.INFO );
		levelMap.put("warn"   , Level.WARN );
		levelMap.put("error"  , Level.ERROR);
	}

	// Logger
	private org.slf4j.Logger logger;

	// Level
	private Level level = Level.TRACE;

	// Log Cosumer
	private Consumer<String> logConsumer;
	
	/**
		Construct a Logger.
	*/
	public SLF4JLogger() {
		logger = LoggerFactory.getLogger(DebugTrace.class);
		logConsumer = logger::trace;
	}

	/**
		Set a logging level
		@param levelStr a logging level string
	*/
	@Override
	public void setLevel(String levelStr) {
		Level level = levelMap.get(levelStr);
		if (level != null) {
			this.level = level;
			logConsumer =
				this.level == Level.TRACE ? logger::trace :
				this.level == Level.DEBUG ? logger::debug :
				this.level == Level.INFO  ? logger::info  :
				this.level == Level.WARN  ? logger::warn  :
				this.level == Level.ERROR ? logger::error : null;
		}
	}

	/**
		Returns whether logging is enabled.
		@return true if logging is enabled; false otherwise
	*/
	@Override
	public boolean isEnabled() {
		return
			level == Level.TRACE ? logger.isTraceEnabled() :
			level == Level.DEBUG ? logger.isDebugEnabled() :
			level == Level.INFO  ? logger.isInfoEnabled () :
			level == Level.WARN  ? logger.isWarnEnabled () :
			level == Level.ERROR ? logger.isErrorEnabled() : false;
	}

	/**
		Output the log message.
		@param message a log message
	*/
	@Override
	public void log(String message) {
		logConsumer.accept(message);
	}
}
