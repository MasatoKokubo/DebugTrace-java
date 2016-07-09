/*
	Jdk.java

	(c) 2015 Masato Kokubo
*/
package org.debugtrace.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.debugtrace.DebugTrace;

/**
	A logger using JDK Logger.

	@since 1.0.0

	@author Masato Kokubo
*/
public class Jdk implements Logger {
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
		Construct a Jdk.
	*/
	public Jdk() {
		logger = java.util.logging.Logger.getLogger(DebugTrace.class.getName());
	}

	/**
		{@inheritDoc}
	*/
	@Override
	public void setLevel(String levelStr) {
		Level level = levelMap.get(levelStr);
		if (level != null)
			this.level = level;
	}

	/**
		{@inheritDoc}
	*/
	@Override
	public boolean isEnabled() {
		return logger.isLoggable(level);
	}

	/**
		{@inheritDoc}
	*/
	@Override
	public void log(String message) {
		logger.log(level, message);
	}
}
