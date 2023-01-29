// PrintSupplierTest.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;


import static org.junit.jupiter.api.Assertions.*;

import org.debugtrace.DebugTrace;
import org.junit.jupiter.api.Test;

public class PrintSupplierTest {
    @Test
    public void printSupplierTest() {
        assertTrue(DebugTrace.print("boolean", () -> true));
        assertTrue(DebugTrace.getLastLog().contains("boolean = true"));

        assertEquals(999_999_999, DebugTrace.print("int", () -> 999_999_999));
        assertTrue(DebugTrace.getLastLog().contains("int = 999999999"));

        assertEquals(-999_999_999_999L, DebugTrace.print("long", () -> -999_999_999_999L));
        assertTrue(DebugTrace.getLastLog().contains("long = (long)-999999999999"));

        assertEquals(999.999D, DebugTrace.print("double", () -> 999.999D));
        assertTrue(DebugTrace.getLastLog().contains("double = (double)999.999"));

    // 3.6.0
    //  assertEquals(0, DebugTrace.print("CalendarMonth", "int", () -> 0));
    //  assertTrue(DebugTrace.getLastLog().contains("int = 0(Calendar.JANUARY)"));
    //
    //  assertEquals(0L, DebugTrace.print("CalendarMonth", "long", () -> 0L));
    //  assertTrue(DebugTrace.getLastLog().contains("long = (long)0(Calendar.JANUARY)"));
    //
    //  assertEquals((Object)(Long)0L, DebugTrace.print("CalendarMonth", "Long", () -> (Object)(Long)0L));
    //  assertTrue(DebugTrace.getLastLog().contains("Long = (Long)0(Calendar.JANUARY)"));
    }

    private void mayThrow() throws Exception {
        throw new Exception("a message");
    }

    @Test
    public void printSupplierTest2() {
        assertFalse(DebugTrace.print("boolean", () -> {mayThrow(); return true;}));
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        assertEquals(0, DebugTrace.print("int", () -> {mayThrow(); return 999_999_999;}));
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        assertEquals(0L, DebugTrace.print("long", () -> {mayThrow(); return -999_999_999_999L;}));
        assertTrue(DebugTrace.getLastLog().contains("a message"));

        assertEquals(0.0, DebugTrace.print("double", () -> {mayThrow(); return 999.999D;}));
        assertTrue(DebugTrace.getLastLog().contains("a message"));

    // 3.6.0
    //  assertEquals(0, DebugTrace.print("CalendarMonth", "int", () -> {mayThrow(); return 0;}));
    //  assertTrue(DebugTrace.getLastLog().contains("a message"));
    //
    //  assertEquals(0L, DebugTrace.print("CalendarMonth", "long", () -> {mayThrow(); return 0L;}));
    //  assertTrue(DebugTrace.getLastLog().contains("a message"));
    //
    //  assertEquals(null, DebugTrace.print("CalendarMonth", "Long", () -> {mayThrow(); return (Object)(Long)0L;}));
    //  assertTrue(DebugTrace.getLastLog().contains("a message"));
    }
}

