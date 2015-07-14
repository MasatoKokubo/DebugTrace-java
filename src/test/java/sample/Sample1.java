package sample;

import org.mkokubo.debugtrace.DebugTrace;

public class Sample1 {
  public static void main(String[] args) {
    DebugTrace.enter(); // 追加

    DebugTrace.print("args", args); // 追加

    DebugTrace.leave(); // 追加
  }
}
