// Example1.java
// (C) 2015 Masato Kokubo
package example;

import org.debugtrace.DebugTrace;

public class ReadmeExample {
    static public class Apple {
        public static final int NO_BRAND = 0;
        public static final int AKANE = 1;
        public static final int AKIYO = 2;
        public static final int AZUSA = 3;
        public static final int YUKARI = 4;
    }

    public static void main(String[] args) {
        int appleBrand = Apple.AKANE;
        DebugTrace.print("appleBrand", appleBrand);
        appleBrand = Apple.AKIYO;
        DebugTrace.print(" 2 appleBrand ", appleBrand);
        appleBrand = Apple.AZUSA;
        DebugTrace.print(" 3 example.appleBrand ", appleBrand);
        appleBrand = Apple.YUKARI;
        DebugTrace.print(" 4 example. appleBrand ", appleBrand);
    }
}
