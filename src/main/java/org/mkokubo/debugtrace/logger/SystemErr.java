/*
	SystemOut.java
	(C) Masato Kokubo
*/
package org.mkokubo.debugtrace.logger;

import org.mkokubo.debugtrace.DebugTrace;

/**
	A logger implementation using System.out.
	@since 1.6.0
	@author Masato Kokubo
*/
public class SystemErr implements Logger {
	/**
		{@inheritDoc}
	*/
	@Override
	public void log(String message) {
		System.err.println(DebugTrace.appendTimestamp(message));
	}
}
