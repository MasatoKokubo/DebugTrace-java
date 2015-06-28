/*
	Log4j2Logger.java

	Created on 2014/10/13.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug;

import org.apache.logging.log4j.LogManager;

/**
	A logger implementation using Log4J2.
	@since 1.0.0
	@author Masato Kokubo
*/
public class Log4j2Logger implements Logger {
	// Logger
	private org.apache.logging.log4j.Logger logger;

	/**
		Construct a Logger.
	*/
	public Log4j2Logger() {
		logger = LogManager.getLogger(DebugTrace.class);
	}

	/**
		Outputs a message string with trace level.
		@param message a message string
	*/
	@Override
	public void trace(String message) {
		logger.trace(message);
	}

	/**
		Returns whether logging at trace level is enabled.
		@return true if logging at trace level is enabled; false otherwise
	*/
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
}
