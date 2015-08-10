/*
	Log4j2Logger.java

	Created on 2014/10/13.
	(C) Masato Kokubo
*/

package org.mkokubo.debugtrace;


/**
	Log4J2 を使用するロガーの実装です。
	@since 1.0.0
	@author 小久保 雅人
*/
public class Log4j2Logger implements Logger {
	/**
		Log4j2Logger を構築します。
	*/
	public Log4j2Logger() {}

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
