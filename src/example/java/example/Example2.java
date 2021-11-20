// Example2.java
// (C) 2015 Masato Kokubo

package example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.stream.IntStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.debugtrace.DebugTrace;

/**
 * Example2
 */
public class Example2 {
    public static void main(String[] args) {
        DebugTrace.enter(); // TODO: Remove after debugging
        DebugTrace.print("args", args); // TODO: Remove after debugging

    Example2 example = new Example2();
        try {
            example.example1();
            example.example2();
            example.example3();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        DebugTrace.leave(); // TODO: Remove after debugging
    }

    private void example1() {
        DebugTrace.enter(); // TODO: Remove after debugging

        Thread[] thread = new Thread[5];
        for (int index = 0; index < thread.length; ++index) {
            thread[index] = new Thread(() -> {
                DebugTrace.enter(); // TODO: Remove after debugging
                try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                DebugTrace.leave(); // TODO: Remove after debugging
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

        DebugTrace.leave(); // TODO: Remove after debugging
    }

    private void example2() {
        DebugTrace.enter(); // TODO: Remove after debugging

        Values values = new Values();
    //  values.valuesOpt = Optional.of(new Values());
        values.valuesOpt = Optional.of(values);

        DebugTrace.print("values", values); // TODO: Remove after debugging

        List<Values> valueList = new ArrayList<>();
        valueList.add(values);
        valueList.add(values);
        DebugTrace.print("valueList", valueList); // TODO: Remove after debugging

        Map<Integer, List<Values>> valueListMap = new LinkedHashMap<>();
        valueListMap.put(1, valueList);
//      valueListMap.put(2, valueList);
        DebugTrace.print("valueListMap", valueListMap); // TODO: Remove after debugging

        Point[] points = IntStream.range(0, 51)
            .mapToObj((index) -> new Point(index, index + 1, index + 2))
            .toArray(Point[]::new);
        DebugTrace.print("points", points); // TODO: Remove after debugging

        Point[] points2 = IntStream.range(0, 10)
            .mapToObj((index) -> new Point(index, index + 1, index + 2))
            .toArray(Point[]::new);
        DebugTrace.print("points2", points2); // TODO: Remove after debugging

        int[] ints = new int[points.length];
        IntStream.range(0, points.length)
            .forEach((index) -> ints[index] = points[index].x() * points[index].y() * (int)points[index].z());
        DebugTrace.print("ints", ints); // TODO: Remove after debugging

        int[][][][] intss = new int[2][3][4][5];
        DebugTrace.print("intss", intss); // TODO: Remove after debugging

        Point p = new Point(10, 11, 12) {};
        DebugTrace.print("p", p); // TODO: Remove after debugging

        List<Object> objects = Arrays.asList(true, 'A', (byte)1, (short)2, 3, 4L, new BigDecimal(5), 6.6F, 7.7D);
        DebugTrace.print("objects", objects); // TODO: Remove after debugging

        DebugTrace.leave(); // TODO: Remove after debugging
    }

    private void example3() throws Exception {
        DebugTrace.enter(); // TODO: Remove after debugging

        DataSource dataSource = BasicDataSourceFactory.createDataSource(new Properties());
        DebugTrace.print("dataSource", dataSource); // TODO: Remove after debugging

        DebugTrace.leave(); // TODO: Remove after debugging
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
        public Optional<String> string2Opt   = Optional      .of   ( "あいうえお"           );
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
        public Boolean       [] c_booleans   = new Boolean       [] {                      false                ,                       true                 };
        public Character     [] c_characters = new Character     [] {                      'A'                  ,                       'B'                  };
        public Byte          [] c_bytes      = new Byte          [] {              (byte )-127                  ,                (byte )127                  };
        public Short         [] c_shorts     = new Short         [] {              (short)-32767                ,                (short)32767                };
        public Integer       [] c_integers   = new Integer       [] {                     -123456789            ,                       123456789            };
        public Long          [] c_longs      = new Long          [] {                     -123456789123456789L  ,                       123456789123456789L  };
        public Float         [] c_floats     = new Float         [] {                     -1234.5678F           ,                       1234.5678F           };
        public Double        [] c_doubles    = new Double        [] {                     -123456789.123456789D ,                       123456789.123456789D };
        public BigInteger    [] bigIntegers  = new BigInteger    [] {new BigInteger     ("-123456789123456789" ), new BigInteger      ("123456789123456789" )};
        public BigDecimal    [] bigDecimals  = new BigDecimal    [] {new BigDecimal     ("-123456789.123456789"), new BigDecimal      ("123456789.123456789")};
        public String        [] strings      = new String        [] {"ABC\b\t\n\f\r\\\"  'EFG"                  , "ABC\b\t\n\f\r\\\"  'EFG"                  };
        public java.util.Date[] utilDates    = new java.util.Date[] {new java.util.Date  ( 0                   ), new java.util.Date  ( 1                   )};
        public java.sql.Date [] sqlDates     = new java.sql.Date [] {new java.sql.Date   ( 0                   ), new java.sql.Date   ( 1                   )};
        public Time          [] times        = new Time          [] {new Time            ( 0                   ), new Time            ( 1                   )};
        public Timestamp     [] timestamps   = new Timestamp     [] {new Timestamp       ( 0                   ), new Timestamp       ( 1                   )};
    }

    public static class Point {
        public  Point self;
        private int x;
        private int y;
        private int z;

        public Point(int x, int y, int z) {
            self = this;
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
        public String toString() {return "(" + x + ", " + y + ", " + z + ")";}
    }
}
