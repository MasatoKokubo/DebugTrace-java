/*
	Logger.java

	Copyright (c) 2015 Masato Kokubo
*/
package org.mkokubo.debugtrace.logger;

/**
	Interface of Logger classes.

	@since 1.0.0

	@author Masato Kokubo
*/
@FunctionalInterface
public interface Logger {
	/**
		Set the logging level

		@param logLevelStr a logging level string
	*/
	default void setLevel(String logLevelStr) {
	}

	/**
		Returns whether logging is enabled.

		@return true if logging is enabled; false otherwise
	*/
	default boolean isEnabled() {
		return true;
	}

	/**
		Output the message to the log.

		@param message a log message
	*/
	void log(String message);
}
