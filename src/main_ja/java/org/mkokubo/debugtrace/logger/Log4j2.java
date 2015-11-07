/*
	Log4j2Logger.java
	(C) 2015 Masato Kokubo
*/

package org.mkokubo.debugtrace.logger;


/**
	Log4J2 を使用するロガーです。

	@since 1.0.0

	@author 小久保 雅人
*/
public class Log4j2 implements Logger {
	/**
		Log4j2Logger を構築します。
	*/
	public Log4j2() {}

	/**
		{@inheritDoc}
	*/
	@Override
	public void setLevel(String levelStr) {}

	/**
		{@inheritDoc}
	*/
	@Override
	public boolean isEnabled() {return false;}

	/**
		{@inheritDoc}
	*/
	@Override
	public void log(String message) {}
}
