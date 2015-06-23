/*
	LoggerFactory.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	Interface for creates a Logger object.
	@since 1.0.0
	@author Masato Kokubo
*/
public interface LoggerFactory {
	/**
		Creates and returns a Logger of the name.
		@param name a name
		@return a Logger
	*/
	public Logger getLogger(String name);
}
