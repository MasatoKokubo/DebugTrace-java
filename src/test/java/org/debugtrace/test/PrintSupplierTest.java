// PrintSupplierTest.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;


import static org.junit.Assert.*;

import org.debugtrace.DebugTrace;
import org.junit.Test;

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
}

