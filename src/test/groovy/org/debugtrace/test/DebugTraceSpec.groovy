// DebugTraceSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test

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
import org.debugtrace.DebugTrace
import spock.lang.*

@Unroll
class DebugTraceSpec extends Specification {
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
        setup:
        DebugTrace.enter()

        when:
        def p = new Point(2, 3);

        then:
        DebugTrace.print('p', p) == p
        DebugTrace.lastLog.indexOf('y: ***') >= 0

        cleanup:
        DebugTrace.leave()
    }

    // defaultPackageSpec
    def defaultPackageSpec() {
        setup:
        DebugTrace.enter()

        when:
        def p = new Point(2, 3);

        then:
        DebugTrace.print('p', p) == p
        DebugTrace.lastLog.indexOf('....Point') >= 0

        cleanup:
        DebugTrace.leave()
    }

    // reflectionClassesSpec
    def reflectionClassesSpec() {
        setup:
        DebugTrace.enter()

        when:
        def p = new Point(2, 3);

        then:
        DebugTrace.print('p', p) == p
        DebugTrace.lastLog.indexOf('Point){') >= 0

        cleanup:
        DebugTrace.leave()
    }

    // printSpec
    def printSpec() {
        setup:
        DebugTrace.enter()

        expect:
        DebugTrace.print('v', false) == false
        DebugTrace.lastLog.indexOf('v = false') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', true) == true
        DebugTrace.lastLog.indexOf('v = true') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (byte)127) == (byte)127
        DebugTrace.lastLog.indexOf('v = (byte)127') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (short)32767) == (short)32767
        DebugTrace.lastLog.indexOf('v = (short)32767') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', 999_999_999) == 999_999_999
        DebugTrace.lastLog.indexOf('v = 999999999') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', 999_999_999_999L) == 999_999_999_999L
        DebugTrace.lastLog.indexOf('v = (long)999999999999') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', 999F) == 999F
        DebugTrace.lastLog.indexOf('v = (float)999.0') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', 999D) == 999D
        DebugTrace.lastLog.indexOf('v = (double)999.0') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'A') == (char)'A'
        DebugTrace.lastLog.indexOf("v = 'A'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\b') == (char)'\b'
        DebugTrace.lastLog.indexOf("v = '\\b'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\t') == (char)'\t'
        DebugTrace.lastLog.indexOf("v = '\\t'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\n') == (char)'\n'
        DebugTrace.lastLog.indexOf("v = '\\n'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\r') == (char)'\r'
        DebugTrace.lastLog.indexOf("v = '\\r'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\f') == (char)'\f'
        DebugTrace.lastLog.indexOf("v = '\\f'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\'') == (char)'\''
        DebugTrace.lastLog.indexOf("v = '\\''") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\u0001') == (char)'\u0001'
        DebugTrace.lastLog.indexOf("v = '\\u0001'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\u001F') == (char)'\u001F'
        DebugTrace.lastLog.indexOf("v = '\\u001F'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', (char)'\u007F') == (char)'\u007F'
        DebugTrace.lastLog.indexOf("v = '\\u007F'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        // 3.0.7
        DebugTrace.print('v', (char)'"') == (char)'"'
        DebugTrace.lastLog.indexOf("v = '\"'") >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', "ABCD") == "ABCD"
        DebugTrace.lastLog.indexOf('v = "ABCD"') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', "ABCDE") == "ABCDE"
        DebugTrace.lastLog.indexOf('v = (length:5)"ABCDE"') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        // 3.0.7
        DebugTrace.print('v', "'ABCDE'") == "'ABCDE'"
        DebugTrace.lastLog.indexOf('v = (length:7)"\'ABCDE\'"') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        // 3.0.7
        DebugTrace.print('v', "ABCDE\b\t\n\r\f'\"\\") == "ABCDE\b\t\n\r\f'\"\\"
        DebugTrace.lastLog.indexOf('v = (length:13)"ABCDE\\b\\t\\n\\r\\f\'\\"\\\\"') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', "\u0001\u001F\u007F") == "\u0001\u001F\u007F"
        DebugTrace.lastLog.indexOf('v = "\\u0001\\u001F\\u007F"') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('Calendar', 'v', (byte)1) == (byte)1
        DebugTrace.lastLog.indexOf('v = (byte)1(Calendar.YEAR)') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('Calendar', 'v', (short)1) == (short)1
        DebugTrace.lastLog.indexOf('v = (short)1(Calendar.YEAR)') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('Calendar', 'v', 1) == 1
        DebugTrace.lastLog.indexOf('v = 1(Calendar.YEAR)') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('Calendar', 'v', 1L) == 1L
        DebugTrace.lastLog.indexOf('v = (long)1(Calendar.YEAR)') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        // 3.0.6
        when:
        def object = new Object()
        then:
        DebugTrace.print('Object', 'v', object) == object
        DebugTrace.lastLog.indexOf('v = (Object){}') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup:
        DebugTrace.leave()
    }

    // simpleTypeNameSpec
    def simpleTypeNameSpec() {
        setup:
        DebugTrace.enter()

        when:
        Object object = null
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = null') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = Boolean.TRUE
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Boolean)true') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = (Character)'A'
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Character)\'A\'') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = (Byte)1
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Byte)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = (Short)1
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Short)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = (Integer)1
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Integer)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = 1L
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Long)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = 1.1F
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Float)1.1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = 1.1D
        then:
        DebugTrace.print('v', (Object)object) == object
        DebugTrace.lastLog.indexOf('v = (Double)1.1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = BigInteger.ONE
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (BigInteger)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = BigDecimal.ONE
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (BigDecimal)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = new java.util.Date(1000L)
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (java.util.Date)1970-01-01 09:00:01') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = new Date(1000L)
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = 1970-01-01') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = new Time(1000L)
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = 09:00:01') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = new Timestamp(1234L)
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = 1970-01-01 09:00:01.234') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = new Name('F', 'L')
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (....Name){') >= 0
        DebugTrace.lastLog.indexOf('first: "F",') >= 0
        DebugTrace.lastLog.indexOf('last: "L"') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = Fruits.APPLE
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Fruits)APPLE') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup:
        DebugTrace.leave()
    }

    // optionalTypeNameSpec
    def optionalTypeNameSpec() {
        setup:
        DebugTrace.enter()

        expect:
        DebugTrace.print('v', OptionalInt.empty()) == OptionalInt.empty()
        DebugTrace.lastLog.indexOf('v = (OptionalInt)empty') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', OptionalInt.of(1)) == OptionalInt.of(1)
        DebugTrace.lastLog.indexOf('v = (OptionalInt)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', OptionalLong.empty()) == OptionalLong.empty()
        DebugTrace.lastLog.indexOf('v = (OptionalLong)empty') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', OptionalLong.of(1L)) == OptionalLong.of(1L)
        DebugTrace.lastLog.indexOf('v = (OptionalLong)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', OptionalDouble.empty()) == OptionalDouble.empty()
        DebugTrace.lastLog.indexOf('v = (OptionalDouble)empty') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', OptionalDouble.of(1.1D)) == OptionalDouble.of(1.1D)
        DebugTrace.lastLog.indexOf('v = (OptionalDouble)1.1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', Optional.<Integer>empty()) == Optional.<Integer>empty()
        DebugTrace.lastLog.indexOf('v = (Optional)empty') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', Optional.of(1)) == Optional.of(1)
        DebugTrace.lastLog.indexOf('v = (Optional)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        DebugTrace.print('v', Optional.of(1L)) == Optional.of(1L)
        DebugTrace.lastLog.indexOf('v = (Optional)(Long)1') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup:
        DebugTrace.leave()
    }

    // arrayTypeNameSpec
    def arrayTypeNameSpec() {
        setup:
        DebugTrace.enter()
        Object object = null
        
        when:
        object = [false, true] as boolean[] 
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (boolean[2])[false, true]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = ['A', 'B'] as char[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (char[2])"AB"') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as byte[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (byte[4])[FF 00 01 02]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as short[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (short[4])[-1, 0, 1, 2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as int[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (int[4])[-1, 0, 1, 2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as long[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (long[4])[-1, 0, 1, 2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object =[-1.1, 0, 1.1, 2.2] as float[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (float[4])[-1.1, 0.0, 1.1, 2.2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1.1, 0, 1.1, 2.2] as double[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (double[4])[-1.1, 0.0, 1.1, 2.2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [false, true] as Boolean[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Boolean[2])[false, true]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = ['A', 'B'] as Character[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Character[2])[\'A\', \'B\']') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as Byte[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Byte[4])[-1, 0, 1, 2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as Short[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Short[4])[-1, 0, 1, 2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as Integer[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Integer[4])[-1, 0, 1, 2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1, 0, 1, 2] as Long[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Long[4])[-1, 0, 1, 2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1.1, 0, 1.1, 2.2] as Float[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Float[4])[-1.1, 0.0, 1.1, 2.2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1.1, 0, 1.1, 2.2] as Double[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Double[4])[-1.1, 0.0, 1.1, 2.2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [-1.1, 0, 1.1, 2.2] as BigDecimal[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (BigDecimal[4])[-1.1, 0, 1.1, 2.2]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = ["A", "B"] as String[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (String[2])["A", "B"]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [Fruits.APPLE, Fruits.ORANGE] as Fruits[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Fruits[2])[(....DebugTraceSpec.Fruits)APPLE, (....DebugTraceSpec.Fruits)ORANGE]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [1, 2, 3, 4, 5] as int[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (int[5])[1, 2, 3, 4, 5]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        when:
        object = [1, 2, 3, 4, 5] as Integer[]
        then:
        DebugTrace.print('v', object) == object
        DebugTrace.lastLog.indexOf('v = (Integer[5])[1, 2, 3, 4, 5]') >= 0
        DebugTrace.lastLog.indexOf(commonSuffix) >= 0

        cleanup:
        DebugTrace.leave()
    }

    // listTypeNameSpec
    def listTypeNameSpec() {
        setup:
        DebugTrace.enter()

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

        cleanup:
        DebugTrace.leave()
    }

    // mapTypeNameSpec
    def mapTypeNameSpec() {
        setup:
        DebugTrace.enter()

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

        cleanup:
        DebugTrace.leave()
    }

    // bytesSpec
    def bytesSpec() {
        setup:
        DebugTrace.enter()

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

         cleanup:
         DebugTrace.leave()
    }

    // printStackSpec
    def printStackSpec() {
        setup:
        DebugTrace.enter()
 
        when: func1()
        then:
        DebugTrace.lastLog.contains('func3')
        DebugTrace.lastLog.contains('func2')
        DebugTrace.lastLog.contains('func1')

        cleanup:
        DebugTrace.leave()
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
