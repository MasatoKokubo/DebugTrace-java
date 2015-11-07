/*
	SystemOut.java

	(C) 2015 Masato Kokubo
*/
package org.mkokubo.debugtrace.logger;

import org.mkokubo.debugtrace.DebugTrace;

/**
	A logger using System.out.

	@since 1.6.0

	@author Masato Kokubo
*/
public class SystemOut implements Logger {
	/**
		{@inheritDoc}
	*/
	@Override
	public void log(String message) {
		System.out.println(DebugTrace.appendTimestamp(message));
	}
}
