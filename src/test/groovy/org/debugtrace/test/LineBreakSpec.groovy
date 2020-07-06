// LineBreakSpec.groovy
// (C) 2015 Masato Kokubo

package org.debugtrace.test;

import java.sql.*;
import java.time.*
import org.debugtrace.DebugTrace;
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

    static class Contact {
        private String firstName
        private String lastName
        private LocalDate birthday
        private String phoneNumber

        public Contact(String firstName, String lastName, Tuple._3<Integer, Integer, Integer> birthday, String phoneNumber) {
            this.firstName   = firstName
            this.lastName    = lastName
            this.birthday    = new LocalDate(birthday.value1(), birthday.value2(), birthday.value3())
            this.phoneNumber = phoneNumber
        } 
    }

    static class Contact4 {
        private Contact contact1
        private Contact contact2
        private Contact contact3
        private Contact contact4

        public Contact4(Contact contact1, Contact contact2, Contact contact3, Contact contact4) {
            this.contact1 = contact1
            this.contact2 = contact2
            this.contact3 = contact3
            this.contact4 = contact4
        }
    }

    def setupSpec() {
        maximumDataOutputWidth = DebugTrace.maximumDataOutputWidth
    }

    def cleanupSpec() {
        DebugTrace.maximumDataOutputWidth = maximumDataOutputWidth
    }

    def lineBreakOfArraySpec() {
        setup:
            DebugTrace.enter()
            DebugTrace.maximumDataOutputWidth = 60
            def contacts = [ 
                new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
                null,
                null
            ] as Contact[]

        when:
            DebugTrace.print('contacts', contacts)

        then:
            DebugTrace.lastLog.contains('[\n|   (....LineBreakSpec.Contact){')
            DebugTrace.lastLog.contains('  firstName:')
            DebugTrace.lastLog.contains(', lastName:')
            DebugTrace.lastLog.contains('  birthday:')
            DebugTrace.lastLog.contains('  phoneNumber:')
            DebugTrace.lastLog.contains('},\n|   (....LineBreakSpec.Contact){')
            DebugTrace.lastLog.contains('},\n|   null, null')

        cleanup:
            DebugTrace.leave()
    }

    def lineBreakOfListSpec() {
        setup:
            DebugTrace.enter()
            DebugTrace.maximumDataOutputWidth = 60
            def contacts = [ 
                new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
                null,
                null
            ]

        when:
            DebugTrace.print('contacts', contacts)

        then:
            DebugTrace.lastLog.contains('[\n|   (....LineBreakSpec.Contact){')
            DebugTrace.lastLog.contains('  firstName:')
            DebugTrace.lastLog.contains(', lastName:')
            DebugTrace.lastLog.contains('  birthday:')
            DebugTrace.lastLog.contains('  phoneNumber:')
            DebugTrace.lastLog.contains('},\n|   (....LineBreakSpec.Contact){')
            DebugTrace.lastLog.contains('},\n|   null, null')

        cleanup:
            DebugTrace.leave()
    }

    def lineBreakOfMapSpec() {
        setup:
            DebugTrace.enter()
            DebugTrace.maximumDataOutputWidth = 60
            def contacts = [ 
                1: new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                2: new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
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
            DebugTrace.lastLog.contains('},\n|   2: (....LineBreakSpec.Contact){\n')

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
            DebugTrace.maximumDataOutputWidth = 60
            def contacts = new Contact4(
                new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222'),
                null,
                null
            )

        when:
            DebugTrace.print('contacts', contacts)

        then:
            DebugTrace.lastLog.contains('{\n|   contact1:')
            DebugTrace.lastLog.contains('  firstName:')
            DebugTrace.lastLog.contains(', lastName:')
            DebugTrace.lastLog.contains('  birthday:')
            DebugTrace.lastLog.contains('  phoneNumber:')
            DebugTrace.lastLog.contains('  contact2:')
            DebugTrace.lastLog.contains('  contact3: null, contact4: null')

        cleanup:
            DebugTrace.leave()
    }
}
