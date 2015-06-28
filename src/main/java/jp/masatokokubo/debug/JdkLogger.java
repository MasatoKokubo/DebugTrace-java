/*
	JdkLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug;

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
		Construct a Logger.
	*/
	public JdkLogger() {
		logger = java.util.logging.Logger.getLogger(DebugTrace.class.getName());
	}

	/**
		Outputs a message string with trace level.
		@param message a message string
	*/
	@Override
	public void trace(String message) {
		logger.log(Level.FINEST, message);
	}

	/**
		Returns whether logging at trace level is enabled.
		@return true if logging at trace level is enabled; false otherwise
	*/
	@Override
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINEST);
	}
}
