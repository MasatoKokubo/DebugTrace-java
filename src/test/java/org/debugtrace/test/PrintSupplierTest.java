// PrintSupplierTest.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;


import static org.junit.jupiter.api.Assertions.*;

import org.debugtrace.DebugTrace;
import org.junit.jupiter.api.Test;

public class PrintSupplierTest {
    @Test
    public void printSupplierTest() {
        DebugTrace.print("boolean", () -> true);
        assertTrue(DebugTrace.getLastLog().contains("boolean = true"));

        DebugTrace.print("int", () -> 999_999_999);
        assertTrue(DebugTrace.getLastLog().contains("int = 999999999"));

        DebugTrace.print("long", () -> -999_999_999_999L);
        assertTrue(DebugTrace.getLastLog().contains("long = (long)-999999999999"));

        DebugTrace.print("double", () -> 999.999D);
        assertTrue(DebugTrace.getLastLog().contains("double = (double)999.999"));


        DebugTrace.print("CalendarMonth", "int", () -> 0);
        assertTrue(DebugTrace.getLastLog().contains("int = 0(Calendar.JANUARY)"));

        DebugTrace.print("CalendarMonth", "long", () -> 0L);
        assertTrue(DebugTrace.getLastLog().contains("long = (long)0(Calendar.JANUARY)"));

        DebugTrace.print("CalendarMonth", "Long", () -> (Object)(Long)0L);
        assertTrue(DebugTrace.getLastLog().contains("Long = (Long)0(Calendar.JANUARY)"));
    }

    private void mayThrow() throws Exception {
        throw new Exception("a message");
    }

    @Test
    public void printSupplierTest2() {
        DebugTrace.print("boolean", () -> {mayThrow(); return true;});
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        DebugTrace.print("int", () -> {mayThrow(); return 999_999_999;});
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        DebugTrace.print("long", () -> {mayThrow(); return -999_999_999_999L;});
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        DebugTrace.print("double", () -> {mayThrow(); return 999.999D;});
        assertTrue(DebugTrace.getLastLog().contains("a message"));


        DebugTrace.print("CalendarMonth", "int", () -> {mayThrow(); return 0;});
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        DebugTrace.print("CalendarMonth", "long", () -> {mayThrow(); return 0L;});
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        DebugTrace.print("CalendarMonth", "Long", () -> {mayThrow(); return (Object)(Long)0L;});
        assertTrue(DebugTrace.getLastLog().contains("a message"));
    }
}

