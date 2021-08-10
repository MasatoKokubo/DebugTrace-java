// Point.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
 
    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}
 