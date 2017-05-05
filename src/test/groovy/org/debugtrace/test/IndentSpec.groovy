// IndentSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test;

import java.sql.Types;
import java.util.Calendar;
import org.debugtrace.DebugTrace;
import spock.lang.*

/**
 * Indent Test.
 *
 * @since 2.4.1
 * @author Masato Kokubo
 */
@Unroll
class IndentSpec extends Specification {
	static class Point {
		int x
		int y
		Point(int x, int y) {
			this.x = x
			this.y = y
		}
	}

	def "indent [byte] array"() {
		setup:
			def dataNestLevel = DebugTrace.state.dataNestLevel

		when:
			DebugTrace.print('[byte] array', (0..<20).inject([]) {
				list, index -> list << (byte)index
			} as byte[])

		then:
			DebugTrace.state.dataNestLevel == dataNestLevel
	}

	def "indent [Point] array"() {
		setup:
			def dataNestLevel = DebugTrace.state.dataNestLevel

		when:
			DebugTrace.print('[Point] array', (0..<10).inject([]) {
				list, index -> list << new Point(index, index * index)
			} as Point[])

		then:
			DebugTrace.state.dataNestLevel == dataNestLevel
	}

	def "indent [Point] list"() {
		setup:
			def dataNestLevel = DebugTrace.state.dataNestLevel

		when:
			DebugTrace.print('[Point] list', (0..<10).inject([]) {
				list, index -> list << new Point(index, index * index)
			})

		then:
			DebugTrace.state.dataNestLevel == dataNestLevel
	}

	def "indent [Integer: Point] map"() {
		setup:
			def dataNestLevel = DebugTrace.state.dataNestLevel

		when:
			DebugTrace.print('[Integer: Point]', (0..<10).inject([:]) {
				map, index ->
				map[index] = new Point(index, index * index)
				return map
			})

		then:
			DebugTrace.state.dataNestLevel == dataNestLevel
	}
}
