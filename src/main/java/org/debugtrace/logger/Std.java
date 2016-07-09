/*
	Std.java
	(c) 2016 Masato Kokubo
*/
package org.debugtrace.logger;

import org.debugtrace.DebugTrace;

/**
	A logger using System.out or System.err.

	@since 2.1.0
	@author Masato Kokubo
*/
public abstract class Std implements Logger {
	/**
		A logger using System.out.
	*/
	public static class Out extends Std {
		/**
			{@inheritDoc}
		*/
		@Override
		public void log(String message) {
			System.out.println(DebugTrace.appendTimestamp(message));
		}
	}

	/**
		A logger using System.err.
	*/
	public static class Err extends Std {
		/**
			{@inheritDoc}
		*/
		@Override
		public void log(String message) {
			System.err.println(DebugTrace.appendTimestamp(message));
		}
	}
}
