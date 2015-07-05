/*
	Logger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug;

/**
	Interface of Logger classes.
	@since 1.0.0
	@author Masato Kokubo
*/
@FunctionalInterface
public interface Logger {
	/**
		Set a logging level
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
		Output the log message.
		@param message a log message
	*/
	void log(String message);
}
