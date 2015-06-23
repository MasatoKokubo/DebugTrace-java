/*
	JdkLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

import java.util.logging.Level;

/**
	A logger implementation using JDK Logger.
	@since 1.0.0
	@author Masato Kokubo
*/
public class JdkLogger implements Logger {
	// Logger
	private java.util.logging.Logger logger;

	/**
		Construct a Logger of the name.
		@param name a name
	*/
	public JdkLogger(String name) {
		logger = java.util.logging.Logger.getLogger(name);
	}

	/**
		Outputs a message string with trace level.
		@param message a message string
	*/
	public void trace(String message) {
		logger.log(Level.FINEST, message);
	}

	/**
		Returns whether logging at trace level is enabled.
		@return true if logging at trace level is enabled; false otherwise
	*/
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINEST);
	}
}
