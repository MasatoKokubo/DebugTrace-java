// README_example.java
// (C) 2015 Masato Kokubo

package example;

import org.debugtrace.DebugTrace;

/**
 * README_example
 */
public class README_example {
	public static class Apple {
		public static final int NO_BRAND = 0;
		public static final int AKANE    = 1;
		public static final int AKIYO    = 2;
		public static final int AZUSA    = 3;
		public static final int YUKARI   = 4;
	}

	public static void main(String[] args) {
		example1();
		example2();
	}

	private static void example1() {
		DebugTrace.enter();

		int appleBrand = Apple.AKANE;
		DebugTrace.print("AppleBrand", "appleBrand", appleBrand);

		DebugTrace.leave();
	}

	private static void example2() {
		DebugTrace.enter();

		int appleBrand = Apple.AKANE;
		DebugTrace.print("appleBrand", appleBrand);
		appleBrand = Apple.AKIYO;
		DebugTrace.print(" 2 appleBrand ", appleBrand);
		appleBrand = Apple.AZUSA;
		DebugTrace.print(" 3 example.appleBrand ", appleBrand);
		appleBrand = Apple.YUKARI;
		DebugTrace.print(" 4 example. appleBrand ", appleBrand);

		DebugTrace.leave();
	}
}
