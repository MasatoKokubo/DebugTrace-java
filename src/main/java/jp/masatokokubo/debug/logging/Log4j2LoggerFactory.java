/*
	Log4j2LoggerFactory.java

	Created on 2014/10/13.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	Creates a Log4j2Logger object.
	@since 1.0.0
	@author Masato Kokubo
*/
public class Log4j2LoggerFactory implements LoggerFactory {
	/**
		Creates and returns a Logger of the name.
		@param name a name
		@return a Logger
	*/
	public Logger getLogger(String name) {
		return new Log4j2Logger(name);
	}
}
