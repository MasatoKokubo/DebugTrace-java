// Example1.java
// (C) 2015 Masato Kokubo
package example;

import java.util.HashMap;
import java.util.Map;

import org.debugtrace.DebugTrace;

public class Example1 {
    private static final Map<Long, Long> fibonacciMap = new HashMap<>();
    static {
        fibonacciMap.put(0L, 0L);
        fibonacciMap.put(1L, 1L);
    }

    public static void main(String[] args) {
        DebugTrace.enter(); // TODO: Remove after debugging
        try {
            if (args.length <= 0)
                throw new IllegalArgumentException("args.length = " + args.length);
            long n = Long.parseLong(args[0]);
            long fibonacci = fibonacci(n);
            System.out.println("fibonacci(" + n + ") = " + fibonacci);
        } catch (Exception e) {
            DebugTrace.print("e", e); // TODO: Remove after debugging
        }
        DebugTrace.leave(); // TODO: Remove after debugging
    }

    public static long fibonacci(long n) {
        DebugTrace.enter(); // TODO: Remove after debugging
        if (n < 0)
            throw new IllegalArgumentException("n (" + n + ") is negative.");
        long fibonacci = 0;
        if (fibonacciMap.containsKey(n)) {
            fibonacci = fibonacciMap.get(n);
            DebugTrace.print("mapped fibonacci(" + n + ")", fibonacci); // TODO: Remove after debugging
        } else {
            fibonacci = fibonacci(n - 2) + fibonacci(n - 1);
            DebugTrace.print("fibonacci(" + n + ")", fibonacci); // TODO: Remove after debugging
            if (fibonacci < 0)
                throw new RuntimeException("Overflow occurred in fibonacci(" + n + ") calculation.");
            fibonacciMap.put(n, fibonacci);
        }
        DebugTrace.leave(); // TODO: Remove after debugging
        return fibonacci;
    }
}
