/*
	Log4jLogger.java
	(C) 2015 Masato Kokubo
*/

package org.debugtrace.logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

/**
 * Log4j を使用するロガーです。
 *
 * @since 1.0.0
 * @author Masato Kokubo
 */
public class Log4j implements Logger {
	/**
	 * Log4jLogger を構築します。
	 */
	public Log4j() {}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLevel(String levelStr) {}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEnabled() {return false;}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void log(String message) {}
}
