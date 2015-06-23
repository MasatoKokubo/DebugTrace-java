/*
	SystemOutLogger.java

	Created on 2014/10/12.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	A logger implementation that outputs to System.err.
	@since 1.0.0
	@author Masato Kokubo
*/
public class SystemOutLogger implements Logger {
	/**
		Construct a Logger by the name.
		@param name a name
	*/
	public SystemOutLogger(String name) {
	}

	/**
		Outputs a message string with trace level.
		@param message a message string
	*/
	public void trace(String message) {
		System.out.println(message);
	}

	/**
		Returns whether logging at trace level is enabled.
		@return true if logging at trace level is enabled; false otherwise
	*/
	public boolean isTraceEnabled() {
		return true;
	}
}
