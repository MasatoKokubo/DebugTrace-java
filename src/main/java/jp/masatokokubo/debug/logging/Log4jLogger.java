/*
	Log4jLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	A logger implementation using Log4J.
	@since 1.0.0
	@author Masato Kokubo
*/
public class Log4jLogger implements Logger {
	// ログ
	private org.apache.log4j.Logger logger;

	/**
		Construct a Logger of the name.
		@param name a name
	*/
	public Log4jLogger(String name) {
		logger = org.apache.log4j.Logger.getLogger(name);
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
