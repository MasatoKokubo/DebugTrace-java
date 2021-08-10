// LineBreakSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test

import java.sql.*
import java.time.*
import org.debugtrace.DebugTrace
import org.debugtrace.helper.Tuple
import spock.lang.*

/**
 * LineBrea Test.
 *
 * @since 3.0.0
 * @author Masato Kokubo
 */
@Unroll
class LineBreakSpec extends Specification {
    @Shared int maximumDataOutputWidth

    def setupSpec() {
        maximumDataOutputWidth = DebugTrace.maximumDataOutputWidth
        DebugTrace.maximumDataOutputWidth = 60
    }

    def cleanupSpec() {
        DebugTrace.maximumDataOutputWidth = maximumDataOutputWidth
    }

    def lineBreakOfArraySpec() {
        setup:
        DebugTrace.enter()
        def contacts = [
            Contact.of('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
            Contact.of('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
            null,
            null
        ] as Contact[]

        when:
        DebugTrace.print('contacts', contacts)

        then:
        DebugTrace.lastLog.contains('[\n|   (....Contact){')
        DebugTrace.lastLog.contains('  firstName:')
        DebugTrace.lastLog.contains(', lastName:')
        DebugTrace.lastLog.contains('  birthday:')
        DebugTrace.lastLog.contains('  phoneNumber:')
        DebugTrace.lastLog.contains('},\n|   (....Contact){')
        DebugTrace.lastLog.contains('},\n|   null, null')

        cleanup:
        DebugTrace.leave()
    }

    def lineBreakOfListSpec() {
        setup:
        DebugTrace.enter()
        def contacts = [ 
            Contact.of('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
            Contact.of('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
            null,
            null
        ]

        when:
        DebugTrace.print('contacts', contacts)

        then:
        DebugTrace.lastLog.contains('[\n|   (....Contact){')
        DebugTrace.lastLog.contains('  firstName:')
        DebugTrace.lastLog.contains(', lastName:')
        DebugTrace.lastLog.contains('  birthday:')
        DebugTrace.lastLog.contains('  phoneNumber:')
        DebugTrace.lastLog.contains('},\n|   (....Contact){')
        DebugTrace.lastLog.contains('},\n|   null, null')

        cleanup:
        DebugTrace.leave()
    }

    def lineBreakOfMapSpec() {
        setup:
        DebugTrace.enter()
        def contacts = [ 
            1: Contact.of('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
            2: Contact.of('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
            3: null,
            4: null
        ]

        when:
        DebugTrace.print('contacts', contacts)

        then:
        DebugTrace.lastLog.contains('firstName: (length:')
        DebugTrace.lastLog.contains('lastName: (length:')
        DebugTrace.lastLog.contains('birthday: (LocalDate)')
        DebugTrace.lastLog.contains('phoneNumber: (length:')
        DebugTrace.lastLog.contains('},\n|   2: (....Contact){\n')

        DebugTrace.lastLog.contains('[\n|   1:')
        DebugTrace.lastLog.contains('  firstName:')
        DebugTrace.lastLog.contains(', lastName:')
        DebugTrace.lastLog.contains('  birthday:')
        DebugTrace.lastLog.contains('  phoneNumber:')
        DebugTrace.lastLog.contains('  2:')
        DebugTrace.lastLog.contains('  3: null, 4: null')

        cleanup:
        DebugTrace.leave()
    }

    def lineBreakOfReflectionSpec() {
        setup:
        DebugTrace.enter()
        def contacts = Contacts.of(
            Contact.of('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
            Contact.of('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
            null,
            null
        )

        when:
        DebugTrace.print('contacts', contacts)

        then:
        DebugTrace.lastLog.contains('\n|   contact1:')
        DebugTrace.lastLog.contains('  firstName:')
        DebugTrace.lastLog.contains(', lastName:')
        DebugTrace.lastLog.contains('  birthday:')
        DebugTrace.lastLog.contains('  phoneNumber:')
        DebugTrace.lastLog.contains('  contact2:')
        DebugTrace.lastLog.contains('  contact3: null, contact4: null')

        cleanup:
        DebugTrace.leave()
    }

    /** @since 3.1.1 */
    def "no line break: name = value"() {
        setup:
        DebugTrace.enter()
        def foo = '000000000011111111112222222222333333333344444444445555555555'
    
        when:
        DebugTrace.print('foo', foo)
    
        then:
        DebugTrace.lastLog.contains('foo = (length:60)"0000000000')

        cleanup:
        DebugTrace.leave()
    }

    /** @since 3.1.1 */
    def "no line break: Object: name: value"() {
        setup:
        DebugTrace.enter()
        def name = new Name('000000000011111111112222222222333333333344444444445555555555', '-')
    
        when:
        DebugTrace.print('name', name)
    
        then:
        DebugTrace.lastLog.contains('first: (length:60)"0000000000')

        cleanup:
        DebugTrace.leave()
    }

    /** @since 3.1.1 */
    def "no line break: Map: key: value"() {
        setup:
        DebugTrace.enter()
        def foo = [:]
        foo[1] = '000000000011111111112222222222333333333344444444445555555555'
    
        when:
        DebugTrace.print('foo', foo)
    
        then:
        DebugTrace.lastLog.contains('1: (length:60)"0000000000')

        cleanup:
        DebugTrace.leave()
    }
}
