// PrintSupplierTest.java
// (C) 2016 Masato Kokubo

package org.debugtrace.test;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.debugtrace.DebugTrace;
import org.junit.Test;

public class PrintSupplierTest {
	@Test
	public void printSupplierTest() {
		DebugTrace.print("boolean", () -> true);
		assertThat(DebugTrace.getLastLog(), containsString("boolean = true"));

		DebugTrace.print("int", () -> 999_999_999);
		assertThat(DebugTrace.getLastLog(), containsString("int = 999999999"));

		DebugTrace.print("long", () -> -999_999_999_999L);
		assertThat(DebugTrace.getLastLog(), containsString("long = (long)-999999999999"));

		DebugTrace.print("double", () -> 999.999D);
		assertThat(DebugTrace.getLastLog(), containsString("double = (double)999.999"));

		DebugTrace.print("CalendarMonth", "int", () -> 0);
		assertThat(DebugTrace.getLastLog(), containsString("int = 0(Calendar.JANUARY)"));

		DebugTrace.print("CalendarMonth", "long", () -> 0L);
		assertThat(DebugTrace.getLastLog(), containsString("long = (long)0(Calendar.JANUARY)"));

		DebugTrace.print("CalendarMonth", "Long", () -> (Object)(Long)0L);
		assertThat(DebugTrace.getLastLog(), containsString("Long = (Long)0(Calendar.JANUARY)"));
	}
}

