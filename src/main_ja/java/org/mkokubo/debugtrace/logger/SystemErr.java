/*
	SystemOut.java
	(C) Masato Kokubo
*/
package org.mkokubo.debugtrace.logger;

import org.mkokubo.debugtrace.DebugTrace;

/**
	System.err を使用するロガーの実装です。
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
