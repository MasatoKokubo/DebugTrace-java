// Std.java
// (C) 2015 Masato Kokubo

package org.debugtrace.logger;

import org.debugtrace.DebugTrace;

/**
 * System.out または System.err に出力するロガーです。
 *
 * @since 2.1.0
 * @author Masato Kokubo
 */
public abstract class Std implements Logger {
	/**
	 * System.out に出力するロガーです。
	 */
	public static class Out extends Std {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void log(String message) {
		}
	}

	/**
	 * System.err に出力するロガーです。
	 */
	public static class Err extends Std {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void log(String message) {
		}
	}
}
