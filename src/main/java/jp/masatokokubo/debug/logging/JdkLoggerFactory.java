/*
	JdkLoggerFactory.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	Creates a JdkLogger object.
	@since 1.0.0
	@author Masato Kokubo
*/
public class JdkLoggerFactory implements LoggerFactory {
	/**
		Creates and returns a Logger of the name.
		@param name a name
		@return a Logger
	*/
	public Logger getLogger(String name) {
		return new JdkLogger(name);
	}
}
