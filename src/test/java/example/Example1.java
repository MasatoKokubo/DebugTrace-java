/*
	Sample1.java
	(C) 2015 Masato Kokubo
*/
package example;

import org.debugtrace.DebugTrace;

public class Example1 {
  public static void main(String[] args) {
    DebugTrace.enter();

    DebugTrace.print("args", args);
    DebugTrace.print("aaa");
    DebugTrace.print("boolean", false);

    DebugTrace.leave();
  }
}
