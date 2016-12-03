/*
	Std.java
	(c) 2016 Masato Kokubo
*/
package org.debugtrace.logger;

import org.debugtrace.DebugTrace;

/**
	System.out または System.err を使用するロガーです。

	@since 2.1.0
	@author Masato Kokubo
*/
public abstract class Std implements Logger {
	/**
		System.out を使用するロガーです。
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
		System.err を使用するロガーです。
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
