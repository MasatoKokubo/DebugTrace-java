/*
	DebugTraceTest.java
	(C) 2015 Masato Kokubo
*/
package org.mkokubo.debugtrace.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.IntStream;

import org.mkokubo.debugtrace.DebugTrace;

/**
	Test class for DebugTrace class.
	@since 1.0.0
	@author Masato Kokubo
*/
public class DebugTraceTest {
	public static void main(String[] args) {
	/**/DebugTrace.addNonPrintProperties(ValuesBase2.class, "sqlDate", "int2Opt", "string2Opt", "nullValue", "nonNullValue");
	/**/DebugTrace.enter();
	/**/DebugTrace.print("args", args);

		DebugTraceTest test = new DebugTraceTest();
		test.test();

	/**/DebugTrace.leave();
	}

	private void test() {
	/**/DebugTrace.enter();

		Values values = new Values();
	//	values.valuesOpt = Optional.of(new Values());
		values.valuesOpt = Optional.of(values);

	/**/DebugTrace.print("values", values);

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


		List<Values> valueList = new ArrayList<>();
		valueList.add(values);
		valueList.add(values);
	/**/DebugTrace.print("valueList", valueList);

		Map<Integer, List<Values>> valueListMap = new LinkedHashMap<>();
		valueListMap.put(1, valueList);
//		valueListMap.put(2, valueList);
	/**/DebugTrace.print("valueListMap", valueListMap);

		Point[] points = IntStream.range(0, 51)
			.mapToObj((index) -> new Point(index, index + 1, index + 2))
			.toArray(Point[]::new);
	/**/DebugTrace.print("points", points);

	/**/DebugTrace.addReflectionTarget(Point.class);
		Point[] points2 = IntStream.range(0, 10)
			.mapToObj((index) -> new Point(index, index + 1, index + 2))
			.toArray(Point[]::new);
	/**/DebugTrace.print("points2", points2);

		int[] ints = new int[points.length];
		IntStream.range(0, points.length)
			.forEach((index) -> ints[index] = points[index].x() * points[index].y() * (int)points[index].z());
	/**/DebugTrace.print("ints", ints);

		int[][][][][][] intss = new int[2][2][2][2][2][2];
	/**/DebugTrace.print("intss", intss);

		Point p = new Point(10, 11, 12) {};
	/**/DebugTrace.print("p", p);


	/**/DebugTrace.leave();
	}

	public static class ValuesBase1 {
		public boolean          booleanValue =                       true                 ;
		public char             charValue    =                       'A'                  ;
		public byte             byteValue    =                (byte )127                  ;
		public short            shortValue   =                (short)32767                ;
		public int              intValue     =                       123456789            ;
		public long             longValue    =                       123456789123456789L  ;
		public float            floatValue   =                       1234.5678F           ;
		public double           doubleValue  =                       123456789.123456789D ;
	}

	public static class ValuesBase2 extends ValuesBase1 {
		public BigInteger       bigInteger   = new BigInteger      ("123456789123456789" );
		public BigDecimal       bigDecimal   = new BigDecimal      ("123456789.123456789");
		public String           string       = "ABC\b\t\n\f\r\\\"  'EFG"                  ;
		public java.util.Date   utilDate     = new java.util.Date  ( 0                   );
		public java.sql.Date    sqlDate      = new java.sql.Date   ( 0                   );
		public Time             time         = new Time            ( 0                   );
		public Timestamp        timestamp    = new Timestamp       ( 0                   );
		public OptionalInt      int1Opt      = OptionalInt   .empty(                     );
		public OptionalInt      int2Opt      = OptionalInt   .of   ( 10                  );
		public OptionalLong     long1Opt     = OptionalLong  .empty(                     );
		public OptionalLong     long2Opt     = OptionalLong  .of   ( 20                  );
		public OptionalDouble   double1Opt   = OptionalDouble.empty(                     );
		public OptionalDouble   double2Opt   = OptionalDouble.of   ( 30.3                );
		public Optional<String> string1Opt   = Optional      .empty(                     );
		public Optional<String> string2Opt   = Optional      .of   ( "ABCDEF"            );
		public Optional<Values> valuesOpt    = Optional      .empty(                     );
		public String           nullValue    = null;
		public String           nonNullValue = "non null";
	}

	public static class Values extends ValuesBase2 {
		public boolean       [] booleans     = new boolean       [] {                      false                ,                       true                 };
		public char          [] chars        = new char          [] {                      'A'                  ,                       'B'                  };
		public byte          [] bytes        = new byte          [] {              (byte )-127                  ,                (byte )127                  };
		public short         [] shorts       = new short         [] {              (short)-32767                ,                (short)32767                };
		public int           [] ints         = new int           [] {                     -123456789            ,                       123456789            };
		public long          [] longs        = new long          [] {                     -123456789123456789L  ,                       123456789123456789L  };
		public float         [] floats       = new float         [] {                     -1234.5678F           ,                       1234.5678F           };
		public double        [] doubles      = new double        [] {                     -123456789.123456789D ,                       123456789.123456789D };
		public BigInteger    [] bigIntegers  = new BigInteger    [] {new BigInteger     ("-123456789123456789" ), new BigInteger      ("123456789123456789" )};
		public BigDecimal    [] bigDecimals  = new BigDecimal    [] {new BigDecimal     ("-123456789.123456789"), new BigDecimal      ("123456789.123456789")};
		public String        [] strings      = new String        [] {"ABC\b\t\n\f\r\\\"  'EFG"                  , "ABC\b\t\n\f\r\\\"  'EFG"                  };
		public java.util.Date[] utilDates    = new java.util.Date[] {new java.util.Date  ( 0                   ), new java.util.Date  ( 1                   )};
		public java.sql.Date [] sqlDates     = new java.sql.Date [] {new java.sql.Date   ( 0                   ), new java.sql.Date   ( 1                   )};
		public Time          [] times        = new Time          [] {new Time            ( 0                   ), new Time            ( 1                   )};
		public Timestamp     [] timestamps   = new Timestamp     [] {new Timestamp       ( 0                   ), new Timestamp       ( 1                   )};
	}

	public static class Point {
		private int x;
		private int y;
		private int z;

		public Point(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		protected int x() {return -x;}
		private int y() {return -y;}
		public long z() {return (long)-z;}

		public Point add(Point p) {return new Point(x + p.x, y + p.y, y + p.z);}
		public Point sub(Point p) {return new Point(x - p.x, y - p.y, y - p.z);}
		public Point mul(Point p) {return new Point(x * p.x, y * p.y, y * p.z);}
		public Point div(Point p) {return new Point(x / p.x, y / p.y, y / p.z);}
		public Point mod(Point p) {return new Point(x % p.x, y % p.y, y % p.z);}
	}
}

