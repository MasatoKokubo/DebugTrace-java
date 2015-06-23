/*
	Log4j2Logger.java

	Created on 2014/10/13.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

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
		Construct a Logger of the name.
		@param name a name
	*/
	public Log4j2Logger(String name) {
		logger = LogManager.getLogger(name);
	}

	/**
		Outputs a message string with trace level.
		@param message a message string
	*/
	public void trace(String message) {
		logger.trace(message);
	}

	/**
		Returns whether logging at trace level is enabled.
		@return true if logging at trace level is enabled; false otherwise
	*/
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
}
