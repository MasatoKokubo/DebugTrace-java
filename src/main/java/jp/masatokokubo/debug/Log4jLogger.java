/*
	Log4jLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

/**
	A logger implementation using Log4J.
	@since 1.0.0
	@author Masato Kokubo
*/
public class Log4jLogger implements Logger {
	// Level Map
	private static final Map<String, Level> levelMap = new HashMap<>();
	static {
		levelMap.put("default", Level.TRACE);
		levelMap.put("trace"  , Level.TRACE);
		levelMap.put("debug"  , Level.DEBUG);
		levelMap.put("info"   , Level.INFO );
		levelMap.put("warn"   , Level.WARN );
		levelMap.put("error"  , Level.ERROR);
		levelMap.put("fatal"  , Level.FATAL);
	}

	// Logger
	private org.apache.log4j.Logger logger;

	// Level
	private Level level = Level.TRACE;

	/**
		Construct a Logger.
	*/
	public Log4jLogger() {
		logger = LogManager.getLogger(DebugTrace.class);
	}

	/**
		Set a logging level
		@param levelStr a logging level string
	*/
	@Override
	public void setLevel(String levelStr) {
		Level level = levelMap.get(levelStr);
		if (level != null)
			this.level = level;
	}

	/**
		Returns whether logging is enabled.
		@return true if logging is enabled; false otherwise
	*/
	@Override
	public boolean isEnabled() {
		return logger.isEnabledFor(level);
	}

	/**
		Output the log message.
		@param message a log message
	*/
	@Override
	public void log(String message) {
		logger.log(level, message);
	}
}
