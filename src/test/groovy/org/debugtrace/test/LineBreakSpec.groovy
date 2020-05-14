// PropertySpec.groovy
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

    static class Contact2 {
        private Contact contact1
        private Contact contact2

        public Contact2(Contact contact1, Contact contact2) {
            this.contact1 = contact1
            this.contact2 = contact2
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
            def contact2 = [ 
                new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222')
            ] as Contact[]

        when:
            DebugTrace.print('contact2', contact2)

        then:
            DebugTrace.lastLog.contains('firstName: (length:')
            DebugTrace.lastLog.contains('lastName: (length:')
            DebugTrace.lastLog.contains('birthday: (LocalDate)')
            DebugTrace.lastLog.contains('phoneNumber: (length:')
            DebugTrace.lastLog.contains('],\n|   (....LineBreakSpec.Contact)[\n')

        cleanup:
            DebugTrace.leave()
    }

    def lineBreakOfListSpec() {
        setup:
            DebugTrace.enter()
            DebugTrace.maximumDataOutputWidth = 60
            def contact2 = [ 
                new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222')
            ]

        when:
            DebugTrace.print('contact2', contact2)

        then:
            DebugTrace.lastLog.contains('firstName: (length:')
            DebugTrace.lastLog.contains('lastName: (length:')
            DebugTrace.lastLog.contains('birthday: (LocalDate)')
            DebugTrace.lastLog.contains('phoneNumber: (length:')
            DebugTrace.lastLog.contains('],\n|   (....LineBreakSpec.Contact)[\n')

        cleanup:
            DebugTrace.leave()
    }

    def lineBreakOfMapSpec() {
        setup:
            DebugTrace.enter()
            DebugTrace.maximumDataOutputWidth = 60
            def contact2 = [ 
                1: new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                2: new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222')
            ]

        when:
            DebugTrace.print('contact2', contact2)

        then:
            DebugTrace.lastLog.contains('firstName: (length:')
            DebugTrace.lastLog.contains('lastName: (length:')
            DebugTrace.lastLog.contains('birthday: (LocalDate)')
            DebugTrace.lastLog.contains('phoneNumber: (length:')
            DebugTrace.lastLog.contains('],\n|   2: (....LineBreakSpec.Contact)[\n')

        cleanup:
            DebugTrace.leave()
    }

    def lineBreakOfReflectionSpec() {
        setup:
            DebugTrace.enter()
            DebugTrace.maximumDataOutputWidth = 60
            def contact2 = new Contact2(
                new Contact('Akane' , 'Apple' , Tuple.of(2020, 1, 1), '080-1111-1111'),
                new Contact('Yukari', 'Apple' , Tuple.of(2020, 2, 2), '080-2222-2222')
            )

        when:
            DebugTrace.print('contact2', contact2)

        then:
            DebugTrace.lastLog.contains('firstName: (length:')
            DebugTrace.lastLog.contains('lastName: (length:')
            DebugTrace.lastLog.contains('birthday: (LocalDate)')
            DebugTrace.lastLog.contains('phoneNumber: (length:')
            DebugTrace.lastLog.contains('],\n|   contact2: (....LineBreakSpec.Contact)[\n')

        cleanup:
            DebugTrace.leave()
    }
}
