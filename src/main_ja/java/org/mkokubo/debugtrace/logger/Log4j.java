/*
	Log4jLogger.java
	(C) Masato Kokubo
*/

package org.mkokubo.debugtrace.logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

/**
	Log4j を使用するロガーの実装です。
	@since 1.0.0
	@author 小久保 雅人
*/
public class Log4j implements Logger {
	/**
		Log4jLogger を構築します。
	*/
	public Log4j() {}

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
