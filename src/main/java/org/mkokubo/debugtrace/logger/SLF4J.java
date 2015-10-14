/*
	SLF4J.java
	(C) Masato Kokubo
*/
package org.mkokubo.debugtrace.logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.log4j.Level;
import org.mkokubo.debugtrace.DebugTrace;
import org.slf4j.LoggerFactory;

/**
	A logger implementation using Log4J2.
	@since 1.1.0
	@author Masato Kokubo
*/
public class SLF4J implements Logger {
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
		Construct a SLF4J.
	*/
	public SLF4J() {
		logger = LoggerFactory.getLogger(DebugTrace.class);
		logConsumer = logger::trace;
	}

	/**
		{@inheritDoc}
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
		{@inheritDoc}
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
		{@inheritDoc}
	*/
	@Override
	public void log(String message) {
		logConsumer.accept(message);
	}
}
