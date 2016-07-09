/*
	Sample1.java

	(c) 2015 Masato Kokubo
*/
package sample;

import org.debugtrace.DebugTrace;

public class Sample1 {
  public static void main(String[] args) {
    DebugTrace.enter();

    DebugTrace.print("args", args);
    DebugTrace.print("aaa");
    DebugTrace.print("boolean", false);

    DebugTrace.leave();
  }
}
