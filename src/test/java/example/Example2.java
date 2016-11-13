/*
	Sample2.java
	(C) 2015 Masato Kokubo
*/
package example;

import org.debugtrace.DebugTrace;

public class Example2 {
  // main
  public static void main(String[] args) {
    DebugTrace.enter();
    DebugTrace.print("args", args);

    sub(args);

    DebugTrace.leave();
  }

  // sub
  private static void sub(String[] args) {
    DebugTrace.enter();

    DebugTrace.print("args", args);

    DebugTrace.leave();
  }
}
