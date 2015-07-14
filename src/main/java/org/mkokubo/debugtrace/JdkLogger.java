/*
	JdkLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package org.mkokubo.debugtrace;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
	A logger implementation using JDK Logger.
	@since 1.0.0
	@author Masato Kokubo
*/
public class JdkLogger implements Logger {
	// Level Map
	private static final Map<String, Level> levelMap = new HashMap<>();
	static {
		levelMap.put("default", Level.FINEST );
		levelMap.put("finest" , Level.FINEST );
		levelMap.put("finer"  , Level.FINER  );
		levelMap.put("fine"   , Level.FINE   );
		levelMap.put("config" , Level.CONFIG );
		levelMap.put("info"   , Level.INFO   );
		levelMap.put("warning", Level.WARNING);
		levelMap.put("severe" , Level.SEVERE );
	}

	// Logger
	private java.util.logging.Logger logger;

	// Level
	private Level level = Level.FINEST;

	/**
		Returns a log string Consumer.
	*/
	public JdkLogger() {
		logger = java.util.logging.Logger.getLogger(DebugTrace.class.getName());
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
		return logger.isLoggable(level);
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
