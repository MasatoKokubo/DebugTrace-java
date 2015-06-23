/*
	Logger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	Interface of Logger classes.
	@since 1.0.0
	@author Masato Kokubo
*/
public interface Logger {
	/**
		Outputs a message string with trace level.
		@param message a message string
	*/
	void trace(String message);

	/**
		Returns whether logging at trace level is enabled.
		@return true if logging at trace level is enabled; false otherwise
	*/
	boolean isTraceEnabled();
}
