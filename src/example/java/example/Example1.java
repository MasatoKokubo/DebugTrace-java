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
        DebugTrace.enter(); // TODO: Remove after debugging

        @SuppressWarnings("unused")
        Point[] points = newArray(Point.class, 2);

        DebugTrace.leave(); // TODO: Remove after debugging
    }

    // newArray
    public static <E> E[] newArray(Class<E> elementType, int length) {
        DebugTrace.enter(); // TODO: Remove after debugging
        DebugTrace.print("elementType", elementType); // TODO: Remove after debugging
        DebugTrace.print("length", length); // TODO: Remove after debugging
        @SuppressWarnings("unchecked")
        E[] array = (E[])Array.newInstance(elementType, length);
        DebugTrace.print("1 array", array); // TODO: Remove after debugging
        try {
            for (int index = 0; index < length; ++index)
                array[index] = elementType.getConstructor().newInstance();
        }
        catch (RuntimeException e) {throw e;}
        catch (Exception e) {throw new RuntimeException(e);}
        DebugTrace.print("2 array", array); // TODO: Remove after debugging
        DebugTrace.leave(); // TODO: Remove after debugging
        return array;
    }

    // Point class
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
