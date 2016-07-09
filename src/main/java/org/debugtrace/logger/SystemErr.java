/*
	SystemErr.java

	(c) 2015 Masato Kokubo
*/
package org.debugtrace.logger;

import org.debugtrace.DebugTrace;

/**
	A logger using System.err.

	@since 1.6.0
	@deprecated as version 2.1.0, use {@link Std.Err}

	@author Masato Kokubo
*/
@Deprecated
public class SystemErr implements Logger {
	/**
		{@inheritDoc}
	*/
	@Override
	public void log(String message) {
		System.err.println(DebugTrace.appendTimestamp(message));
	}
}
