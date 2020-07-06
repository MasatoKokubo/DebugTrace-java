// DebugTraceSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test;

import java.sql.Time
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDate
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import org.debugtrace.DebugTrace;
import spock.lang.*

@Unroll
class DebugTraceSpec extends Specification {
    static class Point {
        int x
        int y
        Point(int x, int y)  {
            this.x = x
            this.y = y
        }
        def String toString() {return "(x: ${x}, y: ${y})"}
    }

    static class Name {
        String first
        String last
        Name(String first, String last)  {
            this.first = first
            this.last = last
        }
    }

    enum Fruits {APPLE, ORANGE, MELON, GRAPE, PINEAPPLE}

    static commonSuffix = ' (' + DebugTraceSpec.class.simpleName + '.groovy:'

    @Shared timeZone = TimeZone.default

    def setupSpec() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+9"))
    }

    def cleanupSpec() {
        TimeZone.default = timeZone
    }

    // enterStringSpec
    def enterLeaveStringSpec() {
        when: DebugTrace.enter()
        then: DebugTrace.lastLog.startsWith('Enter')

        when: DebugTrace.leave()
        then: DebugTrace.lastLog.startsWith('Leave')
    }

    // nonPrintPropertiesSpec
    def nonPrintPropertiesSpec() {
        setup: DebugTrace.enter()

        when:
            def p = new Point(2, 3);
            DebugTrace.print('p', p)

        then:
            DebugTrace.lastLog.indexOf('y: ***') >= 0
            DebugTrace.lastLog.indexOf('metaClass: ***') >= 0

        cleanup: DebugTrace.leave()
    }

    // defaultPackageSpec
    def defaultPackageSpec() {
        setup: DebugTrace.enter()

        when:
            def p = new Point(2, 3);
            DebugTrace.print('p', p)

        then:
            DebugTrace.lastLog.indexOf('....DebugTraceSpec.Point') >= 0
            DebugTrace.lastLog.indexOf('metaClass: ***') >= 0

        cleanup: DebugTrace.leave()
    }

    // reflectionClassesSpec
    def reflectionClassesSpec() {
        setup: DebugTrace.enter()

        when:
            def p = new Point(2, 3);
            DebugTrace.print('p', p)

        then:
            DebugTrace.lastLog.indexOf('Point){') >= 0
            DebugTrace.lastLog.indexOf('metaClass: ***') >= 0

        cleanup: DebugTrace.leave()
    }

    // printSpec
    def printSpec() {
        setup: DebugTrace.enter()

        when: DebugTrace.print('v', true)
        then: DebugTrace.lastLog.indexOf('v = true') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (byte)127)
        then: DebugTrace.lastLog.indexOf('v = (byte)127') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (short)32767)
        then: DebugTrace.lastLog.indexOf('v = (short)32767') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', 999_999_999)
        then: DebugTrace.lastLog.indexOf('v = 999999999') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', 999_999_999_999L)
        then: DebugTrace.lastLog.indexOf('v = (long)999999999999') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', 999F)
        then: DebugTrace.lastLog.indexOf('v = (float)999.0') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', 999D)
        then: DebugTrace.lastLog.indexOf('v = (double)999.0') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', "ABCD")
        then: DebugTrace.lastLog.indexOf('v = "ABCD"') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', "ABCDE")
        then: DebugTrace.lastLog.indexOf('v = (length:5)"ABCDE"') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('Calendar', 'v', (byte)1)
        then: DebugTrace.lastLog.indexOf('v = (byte)1(Calendar.YEAR)') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('Calendar', 'v', (short)1)
        then: DebugTrace.lastLog.indexOf('v = (short)1(Calendar.YEAR)') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('Calendar', 'v', 1)
        then: DebugTrace.lastLog.indexOf('v = 1(Calendar.YEAR)') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('Calendar', 'v', 1L)
        then: DebugTrace.lastLog.indexOf('v = (long)1(Calendar.YEAR)') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup: DebugTrace.leave()
    }

    // simpleTypeNameSpec
    def simpleTypeNameSpec() {
        setup: DebugTrace.enter()

        when: DebugTrace.print('v', null)
        then: DebugTrace.lastLog.indexOf('v = null') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)Boolean.TRUE)
        then: DebugTrace.lastLog.indexOf('v = (Boolean)true') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)(Character)'A')
        then: DebugTrace.lastLog.indexOf('v = (Character)\'A\'') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)(Byte)1)
        then: DebugTrace.lastLog.indexOf('v = (Byte)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)(Short)1)
        then: DebugTrace.lastLog.indexOf('v = (Short)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)(Integer)1)
        then: DebugTrace.lastLog.indexOf('v = (Integer)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)(Long)1L)
        then: DebugTrace.lastLog.indexOf('v = (Long)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)(Float)1.1F)
        then: DebugTrace.lastLog.indexOf('v = (Float)1.1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', (Object)(Double)1.1D)
        then: DebugTrace.lastLog.indexOf('v = (Double)1.1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', BigInteger.ONE)
        then: DebugTrace.lastLog.indexOf('v = (BigInteger)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', BigDecimal.ONE)
        then: DebugTrace.lastLog.indexOf('v = (BigDecimal)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', new java.util.Date(1000L))
        then: DebugTrace.lastLog.indexOf('v = (java.util.Date)1970-01-01 09:00:01') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', new Date(1000L))
        then: DebugTrace.lastLog.indexOf('v = 1970-01-01') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', new Time(1000L))
        then: DebugTrace.lastLog.indexOf('v = 09:00:01') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', new Timestamp(1234L))
        then: DebugTrace.lastLog.indexOf('v = 1970-01-01 09:00:01.234') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:DebugTrace.print('v', new Name('F', 'L'))
        then:
            DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Name){') >= 0
            DebugTrace.lastLog.indexOf('first: "F",') >= 0
            DebugTrace.lastLog.indexOf('last: "L",') >= 0
            DebugTrace.lastLog.indexOf('metaClass: ***') >= 0
            DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', Fruits.APPLE)
        then: DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Fruits)APPLE') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup: DebugTrace.leave()
    }

    // optionalTypeNameSpec
    def optionalTypeNameSpec() {
        setup: DebugTrace.enter()

        when: DebugTrace.print('v', OptionalInt.empty())
        then: DebugTrace.lastLog.indexOf('v = (OptionalInt)empty') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', OptionalInt.of(1))
        then: DebugTrace.lastLog.indexOf('v = (OptionalInt)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', OptionalLong.empty())
        then: DebugTrace.lastLog.indexOf('v = (OptionalLong)empty') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', OptionalLong.of(1L))
        then: DebugTrace.lastLog.indexOf('v = (OptionalLong)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', OptionalDouble.empty())
        then: DebugTrace.lastLog.indexOf('v = (OptionalDouble)empty') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', OptionalDouble.of(1.1D))
        then: DebugTrace.lastLog.indexOf('v = (OptionalDouble)1.1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', Optional.<Integer>empty())
        then: DebugTrace.lastLog.indexOf('v = (Optional)empty') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', Optional.of(1))
        then: DebugTrace.lastLog.indexOf('v = (Optional)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', Optional.of(1L))
        then: DebugTrace.lastLog.indexOf('v = (Optional)(Long)1') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup: DebugTrace.leave()
    }

    // arrayTypeNameSpec
    def arrayTypeNameSpec() {
        setup: DebugTrace.enter()

        when: DebugTrace.print('v', [false, true] as boolean[])
        then: DebugTrace.lastLog.indexOf('v = (boolean[2])[false, true]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', ['A', 'B'] as char[])
        then: DebugTrace.lastLog.indexOf('v = (char[2])"AB"') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as byte[])
        then: DebugTrace.lastLog.indexOf('v = (byte[2])[01 02]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as short[])
        then: DebugTrace.lastLog.indexOf('v = (short[2])[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as int[])
        then: DebugTrace.lastLog.indexOf('v = (int[2])[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as long[])
        then: DebugTrace.lastLog.indexOf('v = (long[2])[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1, 2.2] as float[])
        then: DebugTrace.lastLog.indexOf('v = (float[2])[1.1, 2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1, 2.2] as double[])
        then: DebugTrace.lastLog.indexOf('v = (double[2])[1.1, 2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [false, true] as Boolean[])
        then: DebugTrace.lastLog.indexOf('v = (Boolean[2])[false, true]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', ['A', 'B'] as Character[])
        then: DebugTrace.lastLog.indexOf('v = (Character[2])[\'A\', \'B\']') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as Byte[])
        then: DebugTrace.lastLog.indexOf('v = (Byte[2])[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as Short[])
        then: DebugTrace.lastLog.indexOf('v = (Short[2])[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as Integer[])
        then: DebugTrace.lastLog.indexOf('v = (Integer[2])[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2] as Long[])
        then: DebugTrace.lastLog.indexOf('v = (Long[2])[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1, 2.2] as Float[])
        then: DebugTrace.lastLog.indexOf('v = (Float[2])[1.1, 2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1, 2.2] as Double[])
        then: DebugTrace.lastLog.indexOf('v = (Double[2])[1.1, 2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1, 2.2] as BigDecimal[])
        then: DebugTrace.lastLog.indexOf('v = (BigDecimal[2])[1.1, 2.2]') >= 0

        when: DebugTrace.print('v', ["A", "B"] as String[])
        then: DebugTrace.lastLog.indexOf('v = (String[2])["A", "B"]') >= 0

        when: DebugTrace.print('v', [Fruits.APPLE, Fruits.ORANGE] as Fruits[])
        then: DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Fruits[2])[(....DebugTraceSpec.Fruits)APPLE, (....DebugTraceSpec.Fruits)ORANGE]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2, 3, 4, 5] as int[])
        then:
            DebugTrace.lastLog.indexOf('v = (int[5])[1, 2, 3, 4, 5]') >= 0
            DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2, 3, 4, 5] as Integer[])
        then:
            DebugTrace.lastLog.indexOf('v = (Integer[5])[1, 2, 3, 4, 5]') >= 0
            DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup: DebugTrace.leave()
    }

    // listTypeNameSpec
    def listTypeNameSpec() {
        setup: DebugTrace.enter()

        when: DebugTrace.print('v', [false, true])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[false, true]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(char)'A', (char)'B'])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[\'A\', \'B\']') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(byte)1, (byte)2])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(Byte)1, (Byte)2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(short)1, (short)2])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(Short)1, (Short)2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[1, 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1L, 2L])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(Long)1, (Long)2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1F, 2.2F])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(Float)1.1, (Float)2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1D, 2.2D])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(Double)1.1, (Double)2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [new BigDecimal("1.1"), new BigDecimal("2.2")])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(BigDecimal)1.1, (BigDecimal)2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', ["A", "B"])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)["A", "B"]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [new java.util.Date(1234), new java.util.Date(2345)])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(java.util.Date)1970-01-01 09:00:01.234+09:00, (java.util.Date)1970-01-01 09:00:02.345+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [new Date(0), new Date(24*60*60*1000L)])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[1970-01-01+09:00, 1970-01-02+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [new Time(1234), new Time(2345)])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[09:00:01.234+09:00, 09:00:02.345+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [
                {def t = new Timestamp(1000); t.setNanos(234567890); return t}(),
                {def t = new Timestamp(2000); t.setNanos(345678901); return t}()
            ])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[1970-01-01 09:00:01.234567890+09:00, 1970-01-01 09:00:02.345678901+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [LocalDate.of(2018, 7, 29), LocalDate.of(2018, 7, 30)])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(LocalDate)2018-07-29, (LocalDate)2018-07-30]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [LocalTime.of(1, 23, 45, 678901234), LocalTime.of(12, 34, 56, 789012345)])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(LocalTime)01:23:45.678901234, (LocalTime)12:34:56.789012345]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [LocalDateTime.of(2018, 7, 29, 1, 23, 45, 678901234), LocalDateTime.of(2018, 7, 30, 12, 34, 56, 789012345)])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(LocalDateTime)2018-07-29 01:23:45.678901234, (LocalDateTime)2018-07-30 12:34:56.789012345]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [
                OffsetDateTime.of(2018, 7, 29,  1, 23, 45, 678901234, ZoneOffset.ofHours(-9)),
                OffsetDateTime.of(2018, 7, 30, 12, 34, 56, 789012345, ZoneOffset.ofHours(9))
            ])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(OffsetDateTime)2018-07-29 01:23:45.678901234-09:00, (OffsetDateTime)2018-07-30 12:34:56.789012345+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [
                ZonedDateTime.of(2018, 7, 29,  1, 23, 45, 678901234, ZoneId.of("GMT-9")),
                ZonedDateTime.of(2018, 7, 30, 12, 34, 56, 789012345, ZoneId.of("Asia/Tokyo"))
            ])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(ZonedDateTime)2018-07-29 01:23:45.678901234-09:00 GMT-09:00, (ZonedDateTime)2018-07-30 12:34:56.789012345+09:00 Asia/Tokyo]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [Instant.ofEpochSecond(123, 456789012), Instant.ofEpochSecond(234, 567890123)])
//    then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(Instant)1970-01-01 00:02:03.456789012Z, (Instant)1970-01-01 00:03:54.567890123Z') >= 0
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(Instant)1970-01-01 00:02:03.456789012, (Instant)1970-01-01 00:03:54.567890123') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [Fruits.APPLE, Fruits.ORANGE])
        then: DebugTrace.lastLog.indexOf('v = (ArrayList)[(....DebugTraceSpec.Fruits)APPLE, (....DebugTraceSpec.Fruits)ORANGE]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1, 2, 3, 4, 5])
        then:
            DebugTrace.lastLog.indexOf('v = (ArrayList size:5)[1, 2, 3, 4, 5]') >= 0
            DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup: DebugTrace.leave()
    }

    // mapTypeNameSpec
    def mapTypeNameSpec() {
        setup: DebugTrace.enter()

        when: DebugTrace.print('v', [(false): true, (true): false])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[false: true, true: false]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [((char)'A'): (char)'B'])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[\'A\': \'B\']') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [((byte)1): (byte)2])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(Byte)1: (Byte)2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [((short)1): (short)2])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(Short)1: (Short)2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1: 2])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[1: 2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1L: 2L])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(Long)1: (Long)2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1F: 2.2F])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(Float)1.1: (Float)2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1.1D: 2.2D])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(Double)1.1: (Double)2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', ["A": "B"])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)["A": "B"]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(new BigDecimal("1.1")): new BigDecimal("2.2")])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(BigDecimal)1.1: (BigDecimal)2.2]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(new java.util.Date(1234)): new java.util.Date(2345)])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(java.util.Date)1970-01-01 09:00:01.234+09:00: (java.util.Date)1970-01-01 09:00:02.345+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(new Date(0)): new Date(24*60*60*1000L)])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[1970-01-01+09:00: 1970-01-02+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(new Time(1234)): new Time(2345)])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[09:00:01.234+09:00: 09:00:02.345+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [
                ({def t = new Timestamp(1000); t.setNanos(234567890); return t}()):
                {def t = new Timestamp(2000); t.setNanos(345678901); return t}()
            ])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[1970-01-01 09:00:01.234567890+09:00: 1970-01-01 09:00:02.345678901+09:00]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [(Fruits.APPLE): Fruits.ORANGE])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap)[(....DebugTraceSpec.Fruits)APPLE: (....DebugTraceSpec.Fruits)ORANGE]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when: DebugTrace.print('v', [1: 'A', 2: 'AB', 3: 'ABC', 4: 'ABCD', 5: 'ABCDE'])
        then: DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:5)[1: "A", 2: "AB", 3: "ABC", 4: "ABCD", 5: (length:5)"ABCDE"]') >= 0
              DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup: DebugTrace.leave()
    }

    // bytesSpec
    def bytesSpec() {
        setup: DebugTrace.enter()

        when: DebugTrace.print('v', [] as byte[])
        then: DebugTrace.lastLog.indexOf('v = (byte[0])[]') >= 0
 
        when: DebugTrace.print('v', [0] as byte[])
        then: DebugTrace.lastLog.indexOf('v = (byte[1])[00]') >= 0
 
        when: DebugTrace.print('v', [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14] as byte[])
        then: DebugTrace.lastLog.indexOf('v = (byte[15])[00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E]') >= 0
 
        when: DebugTrace.print('v', [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15] as byte[])
        then: DebugTrace.lastLog.indexOf('v = (byte[16])[00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F]') >= 0

        when: DebugTrace.print('v', [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16] as byte[])
        then: DebugTrace.lastLog
            .indexOf('v = (byte[17])[\n|   00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F\n|   10\n| ]') >= 0

         cleanup: DebugTrace.leave()
    }

    // printStackSpec
    def printStackSpec() {
        setup: DebugTrace.enter()
 
        when: func1()
        then:
            DebugTrace.lastLog.contains('func3')
            DebugTrace.lastLog.contains('func2')
            DebugTrace.lastLog.contains('func1')

        cleanup: DebugTrace.leave()
    }

    private void func1() {
        func2()
    }

    private void func2() {
        func3()
    }

    private void func3() {
        DebugTrace.printStack(10)
    }
}
