// DebugTraceSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test;

import java.sql.Time
import java.sql.Date
import java.sql.Timestamp
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

	@Shared timeZone = TimeZone.default

	def setupSpec() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-0"))
	}

	def cleanupSpec() {
		TimeZone.default = timeZone
	}

	// enterStringSpec
	def enterLeaveStringSpec() {
		when:
			DebugTrace.enter()

		then:
			DebugTrace.lastLog.startsWith('enter')

		when:
			DebugTrace.leave()

		then:
			DebugTrace.lastLog.startsWith('leave')
	}

	// nonPrintPropertiesSpec
	def nonPrintPropertiesSpec() {
		DebugTrace.enter()

		when:
			def p = new Point(2, 3);
			DebugTrace.print('p', p)

		then:
			DebugTrace.lastLog.indexOf('y: ***') >= 0

		DebugTrace.leave()
	}

	// defaultPackageSpec
	def defaultPackageSpec() {
		DebugTrace.enter()

		when:
			def p = new Point(2, 3);
			DebugTrace.print('p', p)

		then:
			DebugTrace.lastLog.indexOf('....DebugTraceSpec.Point') >= 0

		DebugTrace.leave()
	}

	// reflectionClassesSpec
	def reflectionClassesSpec() {
		DebugTrace.enter()

		when:
			def p = new Point(2, 3);
			DebugTrace.print('p', p)

		then:
			DebugTrace.lastLog.indexOf('Point)[') >= 0

		DebugTrace.leave()
	}

	// printSpec
	def printSpec() {
		DebugTrace.enter()

		when:
			DebugTrace.print('v', true)
		then:
			DebugTrace.lastLog.indexOf('v = true') >= 0

		when:
			DebugTrace.print('v', (byte)127)
		then:
			DebugTrace.lastLog.indexOf('v = (byte)127') >= 0

		when:
			DebugTrace.print('v', (short)32767)
		then:
			DebugTrace.lastLog.indexOf('v = (short)32767') >= 0

		when:
			DebugTrace.print('v', 999_999_999)
		then:
			DebugTrace.lastLog.indexOf('v = 999999999') >= 0

		when:
			DebugTrace.print('v', 999_999_999_999L)
		then:
			DebugTrace.lastLog.indexOf('v = (long)999999999999') >= 0

		when:
			DebugTrace.print('v', 999F)
		then:
			DebugTrace.lastLog.indexOf('v = (float)999.0') >= 0

		when:
			DebugTrace.print('v', 999D)
		then:
			DebugTrace.lastLog.indexOf('v = (double)999.0') >= 0

		when:
			DebugTrace.print('Calendar', 'v', (byte)1)
		then:
			DebugTrace.lastLog.indexOf('v = (byte)1(Calendar.YEAR)') >= 0

		when:
			DebugTrace.print('Calendar', 'v', (short)1)
		then:
			DebugTrace.lastLog.indexOf('v = (short)1(Calendar.YEAR)') >= 0

		when:
			DebugTrace.print('Calendar', 'v', 1)
		then:
			DebugTrace.lastLog.indexOf('v = 1(Calendar.YEAR)') >= 0

		when:
			DebugTrace.print('Calendar', 'v', 1L)
		then:
			DebugTrace.lastLog.indexOf('v = (long)1(Calendar.YEAR)') >= 0

		DebugTrace.leave()
	}

	// simpleTypeNameSpec
	def simpleTypeNameSpec() {
		DebugTrace.enter()

		when:
			DebugTrace.print('v', null)
		then:
			DebugTrace.lastLog.indexOf('v = null') >= 0

		when:
			DebugTrace.print('v', (Object)Boolean.TRUE)
		then:
			DebugTrace.lastLog.indexOf('v = (Boolean)true') >= 0

		when:
			DebugTrace.print('v', (Object)(Character)'A')
		then:
			DebugTrace.lastLog.indexOf('v = (Character)\'A\'') >= 0

		when:
			DebugTrace.print('v', (Object)(Byte)1)
		then:
			DebugTrace.lastLog.indexOf('v = (Byte)1') >= 0

		when:
			DebugTrace.print('v', (Object)(Short)1)
		then:
			DebugTrace.lastLog.indexOf('v = (Short)1') >= 0

		when:
			DebugTrace.print('v', (Object)(Integer)1)
		then:
			DebugTrace.lastLog.indexOf('v = (Integer)1') >= 0

		when:
			DebugTrace.print('v', (Object)(Long)1L)
		then:
			DebugTrace.lastLog.indexOf('v = (Long)1') >= 0

		when:
			DebugTrace.print('v', (Object)(Float)1.1F)
		then:
			DebugTrace.lastLog.indexOf('v = (Float)1.1') >= 0

		when:
			DebugTrace.print('v', (Object)(Double)1.1D)
		then:
			DebugTrace.lastLog.indexOf('v = (Double)1.1') >= 0

		when:
			DebugTrace.print('v', BigInteger.ONE)
		then:
			DebugTrace.lastLog.indexOf('v = (BigInteger)1') >= 0

		when:
			DebugTrace.print('v', BigDecimal.ONE)
		then:
			DebugTrace.lastLog.indexOf('v = (BigDecimal)1') >= 0

		when:
			DebugTrace.print('v', new java.util.Date(1000L))
		then:
			DebugTrace.lastLog.indexOf('v = (java.util.Date)1970-01-01 00:00:01') >= 0

		when:
			DebugTrace.print('v', new Date(1000L))
		then:
			DebugTrace.lastLog.indexOf('v = 1970-01-01') >= 0

		when:
			DebugTrace.print('v', new Time(1000L))
		then:
			DebugTrace.lastLog.indexOf('v = 00:00:01') >= 0

		when:
			DebugTrace.print('v', new Timestamp(1234L))
		then:
			DebugTrace.lastLog.indexOf('v = 1970-01-01 00:00:01.234') >= 0

		when:
			DebugTrace.print('v', new Name('F', 'L'))
		then:
			DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Name)[') >= 0
			DebugTrace.lastLog.indexOf('first: "F",') >= 0
			DebugTrace.lastLog.indexOf('last: "L",') >= 0

		when:
			DebugTrace.print('v', Fruits.APPLE)
		then:
			DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Fruits)APPLE') >= 0

		DebugTrace.leave()
	}

	// optionalTypeNameSpec
	def optionalTypeNameSpec() {
		DebugTrace.enter()

		when:
			DebugTrace.print('v', OptionalInt.empty())
		then:
			DebugTrace.lastLog.indexOf('v = (OptionalInt)empty') >= 0

		when:
			DebugTrace.print('v', OptionalInt.of(1))
		then:
			DebugTrace.lastLog.indexOf('v = (OptionalInt)1') >= 0

		when:
			DebugTrace.print('v', OptionalLong.empty())
		then:
			DebugTrace.lastLog.indexOf('v = (OptionalLong)empty') >= 0

		when:
			DebugTrace.print('v', OptionalLong.of(1L))
		then:
			DebugTrace.lastLog.indexOf('v = (OptionalLong)1') >= 0

		when:
			DebugTrace.print('v', OptionalDouble.empty())
		then:
			DebugTrace.lastLog.indexOf('v = (OptionalDouble)empty') >= 0

		when:
			DebugTrace.print('v', OptionalDouble.of(1.1D))
		then:
			DebugTrace.lastLog.indexOf('v = (OptionalDouble)1.1') >= 0

		when:
			DebugTrace.print('v', Optional.<Integer>empty())
		then:
			DebugTrace.lastLog.indexOf('v = (Optional)empty') >= 0

		when:
			DebugTrace.print('v', Optional.of(1))
		then:
			DebugTrace.lastLog.indexOf('v = (Optional)1') >= 0

		when:
			DebugTrace.print('v', Optional.of(1L))
		then:
			DebugTrace.lastLog.indexOf('v = (Optional)(Long)1') >= 0

		DebugTrace.leave()
	}

	// arrayTypeNameSpec
	def arrayTypeNameSpec() {
		DebugTrace.enter()

		when:
			DebugTrace.print('v', [false, true] as boolean[])
		then:
			DebugTrace.lastLog.indexOf('v = (boolean[] length:2)[false, true]') >= 0

		when:
			DebugTrace.print('v', ['A', 'B'] as char[])
		then:
			DebugTrace.lastLog.indexOf('v = (char[] length:2)"AB"') >= 0

		when:
			DebugTrace.print('v', [1, 2] as byte[])
		then:
			DebugTrace.lastLog.indexOf('v = (byte[] length:2)[01 02]') >= 0

		when:
			DebugTrace.print('v', [1, 2] as short[])
		then:
			DebugTrace.lastLog.indexOf('v = (short[] length:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1, 2] as int[])
		then:
			DebugTrace.lastLog.indexOf('v = (int[] length:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1, 2] as long[])
		then:
			DebugTrace.lastLog.indexOf('v = (long[] length:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1.1, 2.2] as float[])
		then:
			DebugTrace.lastLog.indexOf('v = (float[] length:2)[1.1, 2.2]') >= 0

		when:
			DebugTrace.print('v', [1.1, 2.2] as double[])
		then:
			DebugTrace.lastLog.indexOf('v = (double[] length:2)[1.1, 2.2]') >= 0

		when:
			DebugTrace.print('v', [false, true] as Boolean[])
		then:
			DebugTrace.lastLog.indexOf('v = (Boolean[] length:2)[false, true]') >= 0

		when:
			DebugTrace.print('v', ['A', 'B'] as Character[])
		then:
			DebugTrace.lastLog.indexOf('v = (Character[] length:2)[\'A\', \'B\']') >= 0

		when:
			DebugTrace.print('v', [1, 2] as Byte[])
		then:
			DebugTrace.lastLog.indexOf('v = (Byte[] length:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1, 2] as Short[])
		then:
			DebugTrace.lastLog.indexOf('v = (Short[] length:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1, 2] as Integer[])
		then:
			DebugTrace.lastLog.indexOf('v = (Integer[] length:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1, 2] as Long[])
		then:
			DebugTrace.lastLog.indexOf('v = (Long[] length:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1.1, 2.2] as Float[])
		then:
			DebugTrace.lastLog.indexOf('v = (Float[] length:2)[1.1, 2.2]') >= 0

		when:
			DebugTrace.print('v', [1.1, 2.2] as Double[])
		then:
			DebugTrace.lastLog.indexOf('v = (Double[] length:2)[1.1, 2.2]') >= 0

		when:
			DebugTrace.print('v', [1.1, 2.2] as BigDecimal[])
		then:
			DebugTrace.lastLog.indexOf('v = (BigDecimal[] length:2)[1.1, 2.2]') >= 0

		when:
			DebugTrace.print('v', [1, 2, 3, 4] as int[])
		then:
		// 2.4.3
		//	DebugTrace.lastLog.indexOf('v = (int[] length:4)[0: 1, 1: 2, 2: 3, 3: 4]') >= 0
			DebugTrace.lastLog.indexOf('v = (int[] length:4)[1, 2, 3, 4]') >= 0
		////

		when:
			DebugTrace.print('v', [1, 2, 3, 4] as Integer[])
		then:
		// 2.4.3
		//	DebugTrace.lastLog.indexOf('v = (Integer[] length:4)[0: 1, 1: 2, 2: 3, 3: 4]') >= 0
			DebugTrace.lastLog.indexOf('v = (Integer[] length:4)[1, 2, 3, 4]') >= 0
		////
			
		when:
			DebugTrace.print('v', [Fruits.APPLE, Fruits.ORANGE] as Fruits[])
		then:
			DebugTrace.lastLog.indexOf('v = (....DebugTraceSpec.Fruits[] length:2)[(....DebugTraceSpec.Fruits)APPLE, (....DebugTraceSpec.Fruits)ORANGE]') >= 0

		DebugTrace.leave()
	}

	// listTypeNameSpec
	def listTypeNameSpec() {
		DebugTrace.enter()

		when:
			DebugTrace.print('v', [false, true])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[false, true]') >= 0

		when:
			DebugTrace.print('v', [(char)'A', (char)'B'])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[\'A\', \'B\']') >= 0

		when:
			DebugTrace.print('v', [(byte)1, (byte)2])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(Byte)1, (Byte)2]') >= 0

		when:
			DebugTrace.print('v', [(short)1, (short)2])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(Short)1, (Short)2]') >= 0

		when:
			DebugTrace.print('v', [1, 2])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[1, 2]') >= 0

		when:
			DebugTrace.print('v', [1L, 2L])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(Long)1, (Long)2]') >= 0

		when:
			DebugTrace.print('v', [1.1F, 2.2F])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(Float)1.1, (Float)2.2]') >= 0

		when:
			DebugTrace.print('v', [1.1D, 2.2D])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(Double)1.1, (Double)2.2]') >= 0

		when:
			DebugTrace.print('v', [new BigDecimal("1.1"), new BigDecimal("2.2")])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(BigDecimal)1.1, (BigDecimal)2.2]') >= 0

		when:
			DebugTrace.print('v', [new java.util.Date(1000), new java.util.Date(2000)])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(java.util.Date)1970-01-01 00:00:01, (java.util.Date)1970-01-01 00:00:02]') >= 0

		when:
			DebugTrace.print('v', [new Date(0), new Date(24*60*60*1000L)])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[1970-01-01, 1970-01-02]') >= 0

		when:
			DebugTrace.print('v', [new Time(1000), new Time(2000)])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[00:00:01, 00:00:02]') >= 0

		when:
			DebugTrace.print('v', [new Timestamp(1234), new Timestamp(2345)])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[1970-01-01 00:00:01.234, 1970-01-01 00:00:02.345]') >= 0

		when:
			DebugTrace.print('v', [1, 2, 3, 4])
		then:
		// 2.4.3
		//	DebugTrace.lastLog.indexOf('v = (ArrayList size:4)[0: 1, 1: 2, 2: 3, 3: 4]') >= 0
			DebugTrace.lastLog.indexOf('v = (ArrayList size:4)[1, 2, 3, 4]') >= 0
		////

		when:
			DebugTrace.print('v', [Fruits.APPLE, Fruits.ORANGE])
		then:
			DebugTrace.lastLog.indexOf('v = (ArrayList size:2)[(....DebugTraceSpec.Fruits)APPLE, (....DebugTraceSpec.Fruits)ORANGE]') >= 0

		DebugTrace.leave()
	}

	// mapTypeNameSpec
	def mapTypeNameSpec() {
		DebugTrace.enter()

		when:
			DebugTrace.print('v', [(false): true, (true): false])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:2)[false: true, true: false]') >= 0

		when:
			DebugTrace.print('v', [((char)'A'): (char)'B'])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[\'A\': \'B\']') >= 0

		when:
			DebugTrace.print('v', [((byte)1): (byte)2])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(Byte)1: (Byte)2]') >= 0

		when:
			DebugTrace.print('v', [((short)1): (short)2])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(Short)1: (Short)2]') >= 0

		when:
			DebugTrace.print('v', [1: 2])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[1: 2]') >= 0

		when:
			DebugTrace.print('v', [1L: 2L])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(Long)1: (Long)2]') >= 0

		when:
			DebugTrace.print('v', [1.1F: 2.2F])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(Float)1.1: (Float)2.2]') >= 0

		when:
			DebugTrace.print('v', [1.1D: 2.2D])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(Double)1.1: (Double)2.2]') >= 0

		when:
			DebugTrace.print('v', [(new BigDecimal("1.1")): new BigDecimal("2.2")])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(BigDecimal)1.1: (BigDecimal)2.2]') >= 0

		when:
			DebugTrace.print('v', [(new java.util.Date(1000)): new java.util.Date(2000)])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(java.util.Date)1970-01-01 00:00:01: (java.util.Date)1970-01-01 00:00:02]') >= 0

		when:
			DebugTrace.print('v', [(new Date(0)): new Date(24*60*60*1000L)])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[1970-01-01: 1970-01-02]') >= 0

		when:
			DebugTrace.print('v', [(new Time(1000)): new Time(2000)])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[00:00:01: 00:00:02]') >= 0

		when:
			DebugTrace.print('v', [(new Timestamp(1234)): new Timestamp(2345)])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[1970-01-01 00:00:01.234: 1970-01-01 00:00:02.345]') >= 0

		when:
			DebugTrace.print('v', [(Fruits.APPLE): Fruits.ORANGE])
		then:
			DebugTrace.lastLog.indexOf('v = (LinkedHashMap size:1)[(....DebugTraceSpec.Fruits)APPLE: (....DebugTraceSpec.Fruits)ORANGE]') >= 0

		DebugTrace.leave()
	}

}
