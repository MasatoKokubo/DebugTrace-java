/*
	SystemOut.java

	Copyright (c) 2015 Masato Kokubo
*/
package org.mkokubo.debugtrace.logger;

import org.mkokubo.debugtrace.DebugTrace;

/**
	System.err を使用するロガーです。

	@since 1.6.0

	@author Masato Kokubo
*/
public class SystemErr implements Logger {
	/**
		{@inheritDoc}
	*/
	@Override
	public void log(String message) {}
}
