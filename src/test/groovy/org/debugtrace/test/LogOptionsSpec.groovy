// LogOptionsSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test

import org.debugtrace.DebugTrace
import org.debugtrace.LogOptions
import spock.lang.*

/**
 * LogOptions Test.
 *
 * @since 3.7.0
 * @author Masato Kokubo
 */
@Unroll
class LogOptionsSpec extends Specification {
    def constructter() {
        setup:
        def options = new LogOptions()

        expect:
        options.minimumOutputSize   == DebugTrace.minimumOutputSize;
        options.minimumOutputLength == DebugTrace.minimumOutputLength;
        options.collectionLimit     == DebugTrace.collectionLimit;
        options.byteArrayLimit      == DebugTrace.byteArrayLimit;
        options.stringLimit         == DebugTrace.stringLimit;
        options.reflectionNestLimit == DebugTrace.reflectionNestLimit;
    }

    def minimumOutputSize() {
        setup:
        def options = new LogOptions()

        // minimumOutputSize = -1
        when:
        options.minimumOutputSize = -1
        DebugTrace.print('v', [], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:0)[]')

        when:
        DebugTrace.print('v', [1], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:1)[1]')

        // minimumOutputSize = 0
        when:
        options.minimumOutputSize = 0
        DebugTrace.print('v', [], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:0)[]')

        when:
        DebugTrace.print('v', [1], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:1)[1]')

        // minimumOutputSize = 1
        when:
        options.minimumOutputSize = 1
        DebugTrace.print('v', [], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[]')

        when:
        DebugTrace.print('v', [1], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:1)[1]')

        // minimumOutputSize = 2
        when:
        options.minimumOutputSize = 2
        DebugTrace.print('v', [1], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[1]')

        when:
        DebugTrace.print('v', [1, 2], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:2)[1, 2]')
    }

    def minimumOutputLength() {
        setup:
        def options = new LogOptions()

        // minimumOutputLength = -1
        when:
        options.minimumOutputLength = -1
        DebugTrace.print('v', '', options)
        then:
        DebugTrace.lastLog.contains('v = (length:0)""')

        when:
        DebugTrace.print('v', "A", options)
        then:
        DebugTrace.lastLog.contains('v = (length:1)"A"')

        // minimumOutputLength = 0
        when:
        options.minimumOutputLength = 0
        DebugTrace.print('v', '', options)
        then:
        DebugTrace.lastLog.contains('v = (length:0)""')

        when:
        DebugTrace.print('v', 'A', options)
        then:
        DebugTrace.lastLog.contains('v = (length:1)"A"')

        // minimumOutputLength = 1
        when:
        options.minimumOutputLength = 1
        DebugTrace.print('v', '', options)
        then:
        DebugTrace.lastLog.contains('v = ""')

        when:
        DebugTrace.print('v', 'A', options)
        then:
        DebugTrace.lastLog.contains('v = (length:1)"A"')

        // minimumOutputLength = 2
        when:
        options.minimumOutputLength = 2
        DebugTrace.print('v', "A", options)
        then:
        DebugTrace.lastLog.contains('v = "A"')

        when:
        DebugTrace.print('v', "AB", options)
        then:
        DebugTrace.lastLog.contains('v = (length:2)"AB"')
    }

    def collectionLimit() {
        setup:
        def options = new LogOptions()

        // collectionLimit = -1
        when:
        options.collectionLimit = -1
        DebugTrace.print('v', [], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[]')

        when:
        DebugTrace.print('v', [1], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[...]')

        // collectionLimit = 0
        when:
        options.collectionLimit = 0
        DebugTrace.print('v', [], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[]')

        when:
        DebugTrace.print('v', [1], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[...]')

        // collectionLimit = 1
        when:
        options.collectionLimit = 1
        DebugTrace.print('v', [1], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[1]')

        when:
        DebugTrace.print('v', [1, 2], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[1, ...]')

        // collectionLimit = 2
        when:
        options.collectionLimit = 2
        DebugTrace.print('v', [1, 2], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[1, 2]')

        when:
        DebugTrace.print('v', [1, 2, 3], options)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[1, 2, ...]')
    }

    def byteArrayLimit() {
        setup:
        def options = new LogOptions()

        // byteArrayLimit = -1
        when:
        options.byteArrayLimit = -1
        DebugTrace.print('v', [] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[0])[]')

        when:
        DebugTrace.print('v', [1] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[1])[...]')

        // byteArrayLimit = 0
        when:
        options.byteArrayLimit = 0
        DebugTrace.print('v', [] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[0])[]')

        when:
        DebugTrace.print('v', [0x41] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[1])[...]')

        // byteArrayLimit = 1
        when:
        options.byteArrayLimit = 1
        DebugTrace.print('v', [0x41] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[1])[41  A]')

        when:
        DebugTrace.print('v', [0x41, 0x42] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[2])[41 ...  A]')

        // byteArrayLimit = 2
        when:
        options.byteArrayLimit = 2
        DebugTrace.print('v', [0x41, 0x42] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[2])[41 42  AB]')

        when:
        DebugTrace.print('v', [0x41, 0x42, 0x43] as byte[], options)
        then:
        DebugTrace.lastLog.contains('v = (byte[3])[41 42 ...  AB]')
    }

    def stringLimit() {
        setup:
        def options = new LogOptions()

        // stringLimit = -1
        when:
        options.stringLimit = -1
        DebugTrace.print('v', '', options)
        then:
        DebugTrace.lastLog.contains('v = ""')
        
        when:
        DebugTrace.print('v', "A", options)
        then:
        DebugTrace.lastLog.contains('v = "...')

        // stringLimit = 0
        when:
        options.stringLimit = 0
        DebugTrace.print('v', '', options)
        then:
        DebugTrace.lastLog.contains('v = ""')

        when:
        DebugTrace.print('v', 'A', options)
        then:
        DebugTrace.lastLog.contains('v = "..."')

        // stringLimit = 1
        when:
        options.stringLimit = 1
        DebugTrace.print('v', 'A', options)
        then:
        DebugTrace.lastLog.contains('v = "A"')

        when:
        DebugTrace.print('v', 'AB', options)
        then:
        DebugTrace.lastLog.contains('v = "A..."')

        // stringLimit = 2
        when:
        options.stringLimit = 2
        DebugTrace.print('v', "AB", options)
        then:
        DebugTrace.lastLog.contains('v = "AB"')

        when:
        DebugTrace.print('v', "ABC", options)
        then:
        DebugTrace.lastLog.contains('v = "AB..."')
    }

    def reflectionNestLimit() {
        setup:
        def options = new LogOptions()
        def a = new A()

        // reflectionNestLimit = -1
        when:
        options.reflectionNestLimit = -1
        DebugTrace.print('a', a, options)
        then:
        DebugTrace.lastLog.contains('a = (....A){b: ...} (')
        !DebugTrace.lastLog.contains('a = (....A){b: (....B){c: ...}} (')

        // reflectionNestLimit = 0
        when:
        options.reflectionNestLimit = 0
        DebugTrace.print('a', a, options)
        then:
        DebugTrace.lastLog.contains('a = (....A){b: ...} (')
        !DebugTrace.lastLog.contains('a = (....A){b: (....B){c: ...}} (')

        // reflectionNestLimit = 1
        when:
        options.reflectionNestLimit = 1
        DebugTrace.print('a', a, options)
        then:
        DebugTrace.lastLog.contains('a = (....A){b: ...} (')
        !DebugTrace.lastLog.contains('a = (....A){b: (....B){c: ...}} (')

        // reflectionNestLimit = 2
        when:
        options.reflectionNestLimit = 2
        DebugTrace.print('a', a, options)
        then:
        DebugTrace.lastLog.contains('a = (....A){b: (....B){c: ...}} (')
        !DebugTrace.lastLog.contains('a = (....A){b: (....B){c: (....C){v: 1}}} (')

        // reflectionNestLimit = 3
        when:
        options.reflectionNestLimit = 3
        DebugTrace.print('a', a, options)
        then:
        DebugTrace.lastLog.contains('a = (....A){b: (....B){c: (....C){v: 1}}} (')
    }

    def outputSize() {
        when:
        DebugTrace.print('v', [1])
        then:
        DebugTrace.lastLog.contains('v = (ArrayList)[1]')

        when:
        DebugTrace.print('v', [], LogOptions.outputSize)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:0)[]')

        when:
        DebugTrace.print('v', [1], LogOptions.outputSize)
        then:
        DebugTrace.lastLog.contains('v = (ArrayList size:1)[1]')
    }

    def outputLength() {
        when:
        DebugTrace.print('v', 'A')
        then:
        DebugTrace.lastLog.contains('v = "A"')

        when:
        DebugTrace.print('v', '', LogOptions.outputLength)
        then:
        DebugTrace.lastLog.contains('v = (length:0)""')

        when:
        DebugTrace.print('v', 'A', LogOptions.outputLength)
        then:
        DebugTrace.lastLog.contains('v = (length:1)"A"')
    }


}
