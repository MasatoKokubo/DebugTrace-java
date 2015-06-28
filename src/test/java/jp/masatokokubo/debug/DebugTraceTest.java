/*
	DebugTraceTest.java

	Created on 2014/06/21.
	(C) Masato Kokubo
*/
package jp.masatokokubo.debug;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
	Test class for DebugTrace class.
	@since 1.0.0
	@author Masato Kokubo
*/
public class DebugTraceTest {
	public static void main(String[] args) {
	/**/DebugTrace.enter();
	/**/DebugTrace.println("args", args);

		DebugTraceTest test = new DebugTraceTest();
		test.test();

	/**/DebugTrace.leave();
	}

	private void test() {
	/**/DebugTrace.enter();

		Values values = new Values();
		values.booleanValue = true;
		values.charValue    = 'A';
		values.byteValue    = (byte)   127;
		values.shortValue   = (short)32767;
		values.intValue     = 123456789;
		values.longValue    = 123456789123456789L;
		values.floatValue   = 1234.5678F;
		values.doubleValue  = 123456789.123456789D;

		values.bigInteger   = new BigInteger("123456789123456789");
		values.bigDecimal   = new BigDecimal("123456789.123456789");
		values.string       = "ABC\b\t\n\f\r\\\"'EFG";
		values.utilDate     = new java.util.Date(0);
        values.sqlDate      = new java.sql.Date (0);
        values.time         = new Time          (0);
        values.timestamp    = new Timestamp     (0);

		values.string1Opt   = Optional.empty();
		values.string2Opt   = Optional.of("ABCDEF");

	/**/DebugTrace.println("values", values);

		Thread[] thread = new Thread[5];
		for (int index = 0; index < thread.length; ++index) {
			thread[index] = new Thread(() -> {
			/**/DebugTrace.enter();
				try {
					Thread.sleep(200);
				}
				catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			/**/DebugTrace.leave();
			});
		}

		for (int index = 0; index < thread.length; ++index) {
			thread[index].start();
			try {
				Thread.sleep(50);
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}


		List<Values> list = new ArrayList<>();
		list.add(values);
		list.add(values);
	/**/DebugTrace.println("list", list);

		Map<Integer, Values> map = new LinkedHashMap<>();
		map.put(1, values);
		map.put(2, values);
	/**/DebugTrace.println("map", map);

	/**/DebugTrace.leave();
	}
}

class Values {
	public boolean        booleanValue;
	public char           charValue   ;
	public byte           byteValue   ;
	public short          shortValue  ;
	public int            intValue    ;
	public long           longValue   ;
	public float          floatValue  ;
	public double         doubleValue ;

	public BigInteger     bigInteger  ;
	public BigDecimal     bigDecimal  ;
	public String         string      ;
	public java.util.Date utilDate    ;
	public java.sql.Date  sqlDate     ;
	public Time           time        ;
	public Timestamp      timestamp   ;

	public Optional<String> string1Opt;
	public Optional<String> string2Opt;
}
