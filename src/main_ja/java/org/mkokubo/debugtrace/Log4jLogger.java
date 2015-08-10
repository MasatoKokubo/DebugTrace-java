/*
	Log4jLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package org.mkokubo.debugtrace;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

/**
	Log4j を使用するロガーの実装です。
	@since 1.0.0
	@author 小久保 雅人
*/
public class Log4jLogger implements Logger {
	/**
		Log4jLogger を構築します。
	*/
	public Log4jLogger() {}

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
