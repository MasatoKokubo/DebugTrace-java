/*
	JdkLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package org.mkokubo.debugtrace;


/**
	Jdk を使用するロガーの実装です。
	@since 1.0.0
	@author 小久保 雅人
*/
public class JdkLogger implements Logger {
	/**
		JdkLogger を構築します。
	*/
	public JdkLogger() {}

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
