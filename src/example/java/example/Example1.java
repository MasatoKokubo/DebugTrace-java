// Example1.java
// (C) 2015 Masato Kokubo

package example;

import java.lang.reflect.Array;
import org.debugtrace.DebugTrace;

/**
 * Example1
 */
public class Example1 {
	// main
	public static void main(String[] args) {
		DebugTrace.enter(); // added

		@SuppressWarnings("unused")
		Point[] points = newArray(Point.class, 2);

		DebugTrace.leave(); // added
	}

	// newArray
	public static <E> E[] newArray(Class<E> elementType, int length) {
		DebugTrace.enter(); // added
		DebugTrace.print("elementType", elementType); // added
		DebugTrace.print("length", length); // added
		@SuppressWarnings("unchecked")
		E[] array = (E[])Array.newInstance(elementType, length);
		DebugTrace.print("1 array", array); // added
		try {
			for (int index = 0; index < length; ++index)
				array[index] = elementType.getConstructor().newInstance();
		}
		catch (RuntimeException e) {throw e;}
		catch (Exception e) {throw new RuntimeException(e);}
		DebugTrace.print("2 array", array); // added
		DebugTrace.leave(); // added
		return array;
	}

	// Point
	public static class Point {
		private int x;
		private int y;
		public Point() {
		}
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public int getX() {return x;}
		public int getY() {return y;}
	}
}
